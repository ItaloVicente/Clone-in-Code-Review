package org.eclipse.e4.ui.css.swt.resources;

public class ResourceByDefinitionKey {
	private Object key;

	public ResourceByDefinitionKey(Object key) {
		this.key = key;
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof ResourceByDefinitionKey
				&& key.equals(((ResourceByDefinitionKey) obj).key);
	}

	@Override
	public String toString() {
		return key.toString();
	}
}
