/**
 * Program  : Tuple.java<br/>
 * Author   : 12157724@qq.com<br/>
 * Create   : 2018年6月10日 下午8:24:07<br/>
 *
 * All rights reserved.
 */
package com.jin.commons.json;

import java.io.Serializable;

public class Tuple<FIRST, SECOND> implements Serializable {

	private static final long serialVersionUID = 1L;
	public final FIRST first;
	public final SECOND second;

	public Tuple(FIRST first, SECOND second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public String toString() {
		return "Tuple [first=" + first + ", second=" + second + "]";
	}
	
}
