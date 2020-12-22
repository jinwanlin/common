/**
 * Program  : d.java<br/>
 * Author   : i<br/>
 * Create   : 2015年12月10日 下午1:02:58<br/>
 *
 * Copyright 1997-2015 by XJGZ ltd.,
 * All rights reserved.
 */
package com.jin.commons.json;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomObjectMapper extends ObjectMapper {
	/**
	 * 
	 */
	private static final long serialVersionUID = -631258734415889776L;

	public CustomObjectMapper() {
		this.getFactory().setCharacterEscapes(new CustomCharacterEscapes());
	}

	private static class CustomCharacterEscapes extends CharacterEscapes {
		/**
		 * 
		 */
		private static final long serialVersionUID = -5134230096632883009L;
		private final int[] asciiEscapes;

		public CustomCharacterEscapes() {
			int[] esc = CharacterEscapes.standardAsciiEscapesForJSON();
			esc['<'] = CharacterEscapes.ESCAPE_STANDARD;
			esc['>'] = CharacterEscapes.ESCAPE_STANDARD;
			esc['&'] = CharacterEscapes.ESCAPE_STANDARD;
			esc['\''] = CharacterEscapes.ESCAPE_STANDARD;
			asciiEscapes = esc;
		}

		@Override
		public int[] getEscapeCodesForAscii() {
			return asciiEscapes;
		}

		@Override
		public SerializableString getEscapeSequence(int ch) {
			return null;
		}
		
		
	}

}
