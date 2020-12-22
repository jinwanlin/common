/**
 * Program  : UpChildBean.java<br/>
 * Author   : i<br/>
 * Create   : 2018年3月30日 下午6:31:23<br/>
 *
 * Copyright 1997-2013 by xjgz.com ltd.,
 * All rights reserved.
 */
package com.jin.commons.json;

import java.util.List;

public class AppendJsonBean {
	private Object object;
	private List<Tuple<String, Object>> appends ;
	public AppendJsonBean(Object object,List<Tuple<String, Object>> appends) {
		this.object=object;
		this.appends=appends;
	}
	public Object getObject() {
		return object;
	}
	public List<Tuple<String, Object>> getAppends() {
		return appends;
	}
	
}
