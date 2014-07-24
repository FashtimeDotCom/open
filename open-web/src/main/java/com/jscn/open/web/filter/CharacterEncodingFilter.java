package com.jscn.open.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

public class CharacterEncodingFilter extends OncePerRequestFilter {

	
	private final static Logger logger = LoggerFactory.getLogger(CharacterEncodingFilter.class);

	/**
	 * 字符编码
	 */
	private final static String INPUT_CHARSET = "_input_charset=";
	
	
	/**
	 * 字符编码别名
	 */
	private final static String INPUT_CHARSET_ALIAS = "inputCharset=";
	
	/**
	 * 默认编码
	 */
	private final static String DEFAULT_CHARSET = "utf-8";
	
	/**
	 * 可选编码集合
	 */
	private final static List<String> charsetList = new ArrayList<String>();
	
	private String encoding = DEFAULT_CHARSET;
	
	static {
		charsetList.add("UTF-8");
		charsetList.add("GBK");
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		   String queryString = request.getQueryString();
		   if(StringUtils.isNotEmpty(queryString)) {
			   
			   if(queryString.indexOf(INPUT_CHARSET_ALIAS) >= 0){
				   setEncode(queryString, INPUT_CHARSET_ALIAS);
			   }
			   
			   if(queryString.indexOf(INPUT_CHARSET) >= 0){
				   setEncode(queryString, INPUT_CHARSET);
			   }
		   }
		   if(request.getMethod().equals("POST")){
			   request.setCharacterEncoding(this.encoding);
		   }else{
			   request = new GetHttpServletRequest(request);
		   }
		   response.setCharacterEncoding(this.encoding);
		   filterChain.doFilter(request, response);
	}

	
	
	private void setEncode(String queryString,String inputCharset){
		   String rearString = queryString.substring(queryString.indexOf(inputCharset)+inputCharset.length());
	       int lastIndex = rearString.indexOf("&");
	       //_input_charset在最后的情况
	       if(lastIndex > 0){
	    	   this.encoding = rearString.substring(0,lastIndex);
	       }else{
	    	   this.encoding = rearString;
	       }
	       if(!this.charsetList.contains(this.encoding.toUpperCase())){
	    	   this.encoding = DEFAULT_CHARSET;
	       }
	}
	
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * 用来处理get方法提交时的中文问题
	 * @author lifei
	 *
	 */
	class GetHttpServletRequest extends HttpServletRequestWrapper {

		private Map<String,String[]> params = new HashMap<String, String[]>();
		
		private void init() {
			String queryString = getHttpServletRequest().getQueryString();
			if (StringUtils.isNotEmpty(queryString)) {
				String[] query = queryString.split("&");
				String key = null;
				String value = null;
				String[] temp = null;
				String[] strs = null;
				for (String str : query) {
					if (str.indexOf("=") >= 0) {
						strs = str.split("=");
						key = strs[0];
						if (strs.length == 1) {
							logger.debug("{}参数值为空", str);
							value = "";
						}else{
						    value = strs[1];
						}
						if (params.containsKey(key)) {
							temp = params.get(key);
							params.put(key,
									(String[]) ArrayUtils.add(temp, value));
						} else {
							params.put(key, new String[] { value });
						}
					}
				}
			}
		}
		public GetHttpServletRequest(HttpServletRequest request) {
			super(request);
			init();
		}

		/**
		 * 转换由表单读取的数据的内码. 从 ISO 字符转到 utf-8(或gbk).
		 */
		public String encoding(String input) { // 这个是核心方法！！
			try {
				return URLDecoder.decode(input, encoding);
			} catch (Exception ex) {
			}
			return null;
		}

		/**
		 * Return the HttpServletRequest holded by this object.
		 */
		private HttpServletRequest getHttpServletRequest() {
			return (HttpServletRequest) super.getRequest();
		}

		/**
		 * 读取参数 -- 修正了中文问题.
		 */
		public String getParameter(String name) { // 核心方法调用
			if(params.get(name) == null){
				return null;
			}
			return encoding(params.get(name)[0]);
		}

		/**
		 * 读取参数列表 - 修正了中文问题.
		 */
		public String[] getParameterValues(String name) {
			String values[] = params.get(name);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					values[i] = encoding(values[i]); // 核心方法调用
				}
			}
			return values;
		}

	}


}


