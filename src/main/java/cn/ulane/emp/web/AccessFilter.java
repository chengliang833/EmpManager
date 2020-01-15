package cn.ulane.emp.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.ulane.emp.util.Md5;

public class AccessFilter implements Filter {

	public void init(FilterConfig arg0) throws ServletException {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain fchain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		//获取访问路径
		String path = req.getRequestURL().toString();
		//过滤路径 
		if(path.endsWith(".html") || path.endsWith(".do")){
			//login.html过滤
			if(path.endsWith("login.html")){
				fchain.doFilter(req, res);
				return;
			}
			
			//    */login/* 过滤
			if(path.indexOf("/login/")>0){
				fchain.doFilter(req, res);
				return;
			}
			
			//其他是  不通过登录直接访问的情况
			redirect(req, res, fchain);
			return;
		}
		fchain.doFilter(req, res);
	}
	
	private void redirect(HttpServletRequest req, HttpServletResponse res, FilterChain fchain) throws IOException, ServletException {
		//查Cookie是否登录
		Cookie[] cookies = req.getCookies();
		Cookie token = null;
		if(cookies != null){
			for(Cookie cookie : cookies){
				if("token".equals(cookie.getName())){
					token = cookie;
					break;
				}
			}
		}
		//查看cookie是否有值
		if(token != null){
			//找到cookie,检查cookie
			String[] data = token.getValue().split("\\|");
			String time = data[0];
			String md5 = data[1];
			String userAgent = req.getHeader("User-Agent");
			
			String path = req.getRequestURL().toString();
			
			//cookie存在  登陆过,通过请求;
			if(md5.equals(Md5.saltMd5(userAgent + time + "admin"))){
				if(!path.endsWith("user.html") && !path.endsWith("user.do")){
					fchain.doFilter(req, res);
					return;
				}
			}
			if(md5.equals(Md5.saltMd5(userAgent + time + "user"))){
				if(!path.endsWith("index.html") && !path.endsWith("index.do")){
					fchain.doFilter(req, res);
					return;
				}
			}
		}
		String url = req.getContextPath() + "/login.html";
		res.sendRedirect(url);
	}

	public void destroy() {

	}
}
