package com.kobuta.rakuchin.hojinzei.config;

import com.alibaba.fastjson.JSONObject;
import com.kobuta.rakuchin.hojinzei.vo.ResponseVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice(basePackages="com.kobuta.rakuchin.hojinzei.rest")
public class RestResultWrapper implements ResponseBodyAdvice<Object> {

	private static final Logger logger = LoggerFactory.getLogger(RestResultWrapper.class);

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
			ResponseVo result = new ResponseVo(20000,body, "success");
			// 对返回值是string的rest进行特殊处理，以避免转型错误：ResponseVo→String
			if (body instanceof String) {
				return JSONObject.toJSONString(result);
			}
	        return result;
	}


	@ExceptionHandler(value = HttpClientErrorException.class)
	@ResponseBody
	public Object httpClientErrorExceptionHandler(HttpServletRequest request, HttpClientErrorException e, HttpServletResponse response) {


		String excStr = e.getResponseBodyAsString();
		JSONObject jsonObject = JSONObject.parseObject(excStr);
		String msg = jsonObject.getString("error_description");
		String error = jsonObject.getString("error");
		if (StringUtils.isEmpty(msg)) {
			msg = "系统异常";
		}
		if (StringUtils.isEmpty(error)) {
			error = "";
		}
		logger.error("系统异常:" + msg, e);
		ResponseVo result = new ResponseVo(-1, error,msg);
		return result;
	}

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object logicExceptionHandler(HttpServletRequest request, Exception e, HttpServletResponse response) {

        String msg = e.getMessage();
		logger.error("系统异常:" + msg, e);
        if (StringUtils.isEmpty(msg)) {
			msg = "系统异常";
		}
    	ResponseVo result = new ResponseVo(-1, msg,msg);
        return result;
    }


}
