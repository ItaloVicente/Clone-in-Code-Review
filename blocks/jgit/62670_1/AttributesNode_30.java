
package org.eclipse.jgit.attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jgit.attributes.Attribute.State;

public final class Attributes {
	private final Map<String

	public Attributes(Attribute... attributes) {
		if (attributes != null) {
			for (Attribute a : attributes) {
				put(a);
			}
		}
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Attribute get(String key) {
		return map.get(key);
	}

	public Collection<Attribute> getAll() {
		return new ArrayList<>(map.values());
	}

	public void put(Attribute a) {
		map.put(a.getKey()
	}

	public void remove(String key) {
		map.remove(key);
	}

	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public Attribute.State getState(String key) {
		Attribute a = map.get(key);
		return a != null ? a.getState() : Attribute.State.UNSPECIFIED;
	}

	public boolean isSet(String key) {
		return (getState(key) == State.SET);
	}

	public boolean isUnset(String key) {
		return (getState(key) == State.UNSET);
	}

	public boolean isUnspecified(String key) {
		return (getState(key) == State.UNSPECIFIED);
	}

	public boolean isCustom(String key) {
		return (getState(key) == State.CUSTOM);
	}

	public String getValue(String key) {
		Attribute a = map.get(key);
		return a != null ? a.getValue() : null;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(getClass().getSimpleName());
		for (Attribute a : map.values()) {
			buf.append(a.toString());
		}
		return buf.toString();
	}

	@Override
	public int hashCode() {
		return map.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Attributes))
			return false;
		Attributes other = (Attributes) obj;
		return this.map.equals(other.map);
	}

}
