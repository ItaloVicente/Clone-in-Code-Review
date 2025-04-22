package com.couchbase.client.protocol.views;

import java.util.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class ComplexKey {

	private final List<Object> components;

	private static final Object EMPTY_OBJECT = new Object();
	private static final Object[] EMPTY_ARRAY = new Object[0];

  public static ComplexKey of(Object... components) {
		return new ComplexKey(components);
	}

  public static Object emptyObject() {
		return EMPTY_OBJECT;
	}

  public static Object[] emptyArray() {
		return EMPTY_ARRAY;
	}

	private ComplexKey(Object[] components) {
		this.components = Arrays.asList(components);
	}

  public String toJson() {
    JSONArray key = new JSONArray();
		for (Object component : components) {
			if (component == EMPTY_OBJECT) {
        key.put(new JSONObject());
			} else {
				key.put(component);
			}
		}
		return key.toString();
	}
}
