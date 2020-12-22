/**
 * Program  : CustomSerializer.java<br/>
 * Author   : i<br/>
 * Create   : 2018年3月30日 下午3:31:03<br/>
 *
 * Copyright 1997-2013 by xjgz.com ltd.,
 * All rights reserved.
 */
package com.jin.commons.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;

public class AppendJsonSerializer extends JsonSerializer<AppendJsonBean> {

	public AppendJsonSerializer() {
	}

	@Override
	public void serialize(AppendJsonBean value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		JavaType type = provider.constructType(value.getObject().getClass());
		BeanDescription bd = provider.getConfig().introspect(type);
		JsonSerializer<Object> ser = BeanSerializerFactory.instance.findBeanSerializer(provider, type, bd);
		ser.unwrappingSerializer(null).serialize(value.getObject(), jgen, provider);
		for (Tuple<String, Object> kv : value.getAppends()) {
			jgen.writeObjectField(kv.first, kv.second);
		}
		jgen.writeEndObject();
	}

	@Override
	public Class<AppendJsonBean> handledType() {
		return AppendJsonBean.class;
	}

}
