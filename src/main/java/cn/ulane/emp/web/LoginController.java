package cn.ulane.emp.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ulane.emp.entity.Admin;
import cn.ulane.emp.entity.User;
import cn.ulane.emp.service.LoginService;
import cn.ulane.emp.util.Md5;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/testaaa.do")
	@ResponseBody
	public JsonResult testaaa(Integer times, HttpServletRequest request, HttpServletResponse response) {
		for(int i=0; i<times; i++){
			System.out.println(System.currentTimeMillis()+":afdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsdafdsfraegsjdlfghkdahfkdsfkagfsd");
		}
		return new JsonResult("111");
	}
	
	@RequestMapping("/login.do")
	@ResponseBody
	public JsonResult login(String username, String password, String code, HttpServletRequest request, HttpServletResponse response) {
		String servCode = (String)request.getSession().getAttribute("code");
		if(servCode == null || !servCode.equalsIgnoreCase(code))
			return new JsonResult("验证码错误");
		Integer servCodeTime = (Integer) request.getSession().getAttribute("time");
		if(servCodeTime == null || servCodeTime == 0 ){
			return new JsonResult("验证码已失效");
		}
		servCodeTime--;
		request.getSession().setAttribute("time", servCodeTime);

		Object obj = loginService.login(username, password);
		
		//设置身份cookie
		if(obj instanceof Admin){
			setCookie("admin",request, response);
			return new JsonResult(JsonResult.ADMIN, obj);
		}
		if(obj instanceof User){
			setCookie("user",request, response);
			return new JsonResult(JsonResult.USER, obj);
		}
		return null;
	}

	private void setCookie(String identity, HttpServletRequest request, HttpServletResponse response) {
		//cookie绑定
		String userAgent = request.getHeader("User-Agent");
		long now = System.currentTimeMillis();
		String token = Md5.saltMd5(userAgent + now + identity);
		Cookie cookie = new Cookie("token", now + "|" + token);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
	
	@RequestMapping("/exit.do")
	@ResponseBody
	public JsonResult exit(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("token", null);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return new JsonResult("退出");
	}
	
	@RequestMapping(value="/code.do", produces = "image/png")
	@ResponseBody
	public byte[] code(HttpServletRequest request) throws IOException {
		byte[] buf;
		//创建图片对象
		BufferedImage img = new BufferedImage(80, 30, BufferedImage.TYPE_3BYTE_BGR);
		
		//填充背景为灰色
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				img.setRGB(x, y, 0xEEEEEE);
			}
		}
		
//		//绘制1000个麻点
//		for (int i = 0; i < 1000; i++) {
//			int x = (int) (Math.random() * 80);
//			int y = (int) (Math.random() * 30);
//			int rgb = (int) (Math.random() * 0xffffff);
//			img.setRGB(x, y, rgb);
//		}
		
		//获取画笔对象
		Graphics2D g = img.createGraphics();
		//画笔调制随机色
		int rgb = (int) (Math.random() * 0xffffff);
		g.setColor(new Color(rgb));
		//设置画笔字体大小
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 25);
		g.setFont(font);

		// 抗锯齿，平滑绘制
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		//随机验证码绑定到session
		String code = randomCode();
		request.getSession().setAttribute("code", code);
		request.getSession().setAttribute("time", 1);
		//验证码绘制到随机位置
		int x = (int) (Math.random() * 10);
		int y = (int) (Math.random() * 5);
		g.drawString(code, 3 + x, 28 - y);
		
//		// 绘制5条乱线
//		for (int i = 0; i < 5; i++) {
//			int x1 = (int) (Math.random() * 80);
//			int y1 = (int) (Math.random() * 30);
//			int x2 = (int) (Math.random() * 80);
//			int y2 = (int) (Math.random() * 30);
//			rgb = (int) (Math.random() * 0xffffff);
//			g.setColor(new Color(rgb));
//			g.drawLine(x1, y1, x2, y2);
//		}
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		out.close();
		buf = out.toByteArray();
		return buf;
	}

	/** 随机生成验证码 */
	private String randomCode() {
//		System.out.println("adwdsadasd");
		String chs = "4567ABCDEFHJKLXYabcdrhknp";
		char[] code = new char[4];
		for (int i = 0; i < code.length; i++) {
			int index = (int) (Math.random() * chs.length());
			code[i] = chs.charAt(index);
		}
		return new String(code);
	}

	@RequestMapping("/checkCode.do")
	@ResponseBody
	public JsonResult<Boolean> checkCode(String code, HttpServletRequest request) {
		String serverCode = (String) request.getSession().getAttribute("code");
		if (serverCode == null || !serverCode.equalsIgnoreCase(code)) {
			return new JsonResult<Boolean>("验证码错误");
		}
		return new JsonResult<Boolean>(true);
	}
}
