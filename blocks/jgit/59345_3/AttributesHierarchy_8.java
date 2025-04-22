
package org.eclipse.jgit.attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jgit.attributes.Attribute.State;

public final class AttributeSet {
	private final Map<String

	public AttributeSet(Attribute... attributes) {
		if (attributes != null) {
			for (Attribute a : attributes) {
				putAttribute(a);
			}
		}
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Attribute getAttribute(String key) {
		return map.get(key);
	}

	public Collection<Attribute> getAttributes() {
		return new ArrayList<>(map.values());
	}

	public void putAttribute(Attribute a) {
		map.put(a.getKey()
	}

	public void removeAttribute(String key) {
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
		Attribute a = map.get(key);
		return a != null ? a.getState() == State.SET : false;
	}

	public boolean isUnset(String key) {
		Attribute a = map.get(key);
		return a != null ? a.getState() == State.UNSET : false;
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
		if (!(obj instanceof AttributeSet))
			return false;
		AttributeSet other = (AttributeSet) obj;
		return this.map.equals(other.map);
	}

}
