/**
 * Program  : JsonUtil.java<br/>
 * Author   : i<br/>
 * Create   : 2013-8-16 下午6:59:02<br/>
 *
 * Copyright 1997-2013 by xjgz.com ltd.,
 * All rights reserved.
 */
package com.jin.commons.json;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonUtil {
	private static Logger logger = Logger.getLogger(JsonUtil.class);
	private static ObjectMapper mapper = new CustomObjectMapper();
	private static SimpleModule module = new SimpleModule();
	private static JsonUtil defaultJsonUtil = new JsonUtil();
	private static JsonUtil appenderJsonUtil = null;
	public static class JsonBuilder {
		private ObjectMapper mapper = new ObjectMapper();
		private ObjectNode root = mapper.createObjectNode();
		private ObjectNode current = root;
		private boolean isEmpty = true;

		JsonBuilder() {

		}

		public void add(String fieldName, JsonBuilder builder) {
			current.set(fieldName, builder.current);
			return;
		}

		public JsonBuilder add(String key, Object value) {
			root.putPOJO(key, value);
			isEmpty = false;
			return this;
		}

		public JsonBuilder child(String nodeName, String key, String value) {
			ObjectNode objectNode = mapper.createObjectNode();
			objectNode.put(key, value);
			current.set(nodeName, objectNode);
			current = objectNode;
			isEmpty = false;
			return this;
		}

		public boolean isEmpty() {
			return isEmpty;
		}

		public String toJson() throws Exception {
			StringWriter writer = new StringWriter();
			try {
				mapper.writeValue(writer, root);
				return writer.toString();
			} catch (IOException e) {
				logger.error(e, e);
				throw new Exception(e);
			}
		}
	}

	public static JsonUtil instance() {
		return defaultJsonUtil;
	}

	public static synchronized JsonUtil instance(AppendJsonSerializer appender) {
		return appenderJsonUtil = (appenderJsonUtil == null) ? new JsonUtil(appender) : appenderJsonUtil;
	}

	public <T> List<T> json2List(String json, Class<T> clazz) throws Exception {
		try {
			CustomObjectMapper mapper = new CustomObjectMapper();
			mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
			CollectionType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
			return mapper.readValue(json, type);
		} catch (IOException e) {
			logger.error("ERROR JSON:" + json);
			logger.error(e, e);
			throw new Exception(e);
		}
	}

	public Map<String, Object> json2Map(String json) throws Exception {
		try {
			return new CustomObjectMapper().readValue(json, new TypeReference<HashMap<String, Object>>() {
			});
		} catch (IOException e) {
			logger.error("ERROR JSON:" + json);
			logger.error(e, e);
			throw new Exception(e);
		}
	}

	public JsonNode jsonTree(String json) throws Exception {
		try {
			return new ObjectMapper().readTree(json);
		} catch (IOException e) {
			logger.error("ERROR JSON:'" + json + "'");
			logger.error(e, e);
			throw new Exception(e);
		}
	}

	public void removeNode(JsonNode root, String nodeName) {
		ObjectNode node = (ObjectNode) root;
		node.remove(nodeName);
	}

	public ObjectNode removeNode(String json, String nodeName) throws Exception {
		ObjectNode node = (ObjectNode) jsonTree(json);
		node.remove(nodeName);
		return node;
	}

	public String removeNode2Str(String json, String nodeName) throws Exception {
		return removeNode(json, nodeName).toString();
	}


	private JsonUtil() {
		logger.info("init default" + this.getClass());
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	}

	public JsonUtil(AppendJsonSerializer ser) {
		logger.info("init AppendJsonSerializer jsonutil");
		module.addSerializer(ser);
		mapper.registerModule(module);
	}

	public static String bean2json(Object object) throws Exception {
		try {
			// mapper.registerModule(new DefaultScalaModule());
			StringWriter writer = new StringWriter();
			mapper.writeValue(writer, object);
			return writer.toString();
		} catch (IOException e) {
			logger.error(e, e);
			throw new Exception(e);
		}
	}

	public JsonBuilder createJsonBuilder() {
		return new JsonBuilder();
	}

	public static <T> T json2Bean(String json, Class<T> clazz) throws Exception {
		try {
			mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			logger.error("ERROR JSON:" + json);
			logger.error(e, e);
			throw new Exception(e);
		}
	}

	public String list2Json(List<?> list) throws Exception {
		return bean2json(list);
	}

	public String map2Bean(Map<String, Object> map) throws Exception {
		return bean2json(map);
	}

}
