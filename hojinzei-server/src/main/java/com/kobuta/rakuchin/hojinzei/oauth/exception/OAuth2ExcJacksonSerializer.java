package com.kobuta.rakuchin.hojinzei.oauth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class OAuth2ExcJacksonSerializer extends StdSerializer<OAuth2Exc> {
	
	private static final long serialVersionUID = 8869876352980952780L;

	public OAuth2ExcJacksonSerializer() {
        super(OAuth2Exc.class);
    }

    @Override
    public void serialize(OAuth2Exc value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        gen.writeStartObject();
        gen.writeStringField("code", String.valueOf(value.getHttpErrorCode()));
        gen.writeStringField("path", request.getServletPath());
        gen.writeStringField("timestamp", String.valueOf(new Date().getTime()));
        String oAuth2ErrorCode = value.getOAuth2ErrorCode();
        if (oAuth2ErrorCode != null) {
        	oAuth2ErrorCode = HtmlUtils.htmlEscape(oAuth2ErrorCode);
        }
        String message = value.getMessage();
        gen.writeStringField("oAuth2Error", oAuth2ErrorCode);
        gen.writeStringField("message", message);

        if (value.getAdditionalInformation()!=null) {
            for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
                String key = entry.getKey();
                String add = entry.getValue();
                gen.writeStringField(key, add);
            }
        }
        gen.writeEndObject();
    }
}
