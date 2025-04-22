package org.eclipse.ui.internal.dialogs;

import org.eclipse.core.runtime.Assert;

final class PreferenceHistoryEntry {
	private String id;
	private String label;
	private Object argument;
	
	public PreferenceHistoryEntry(String id, String label, Object argument) {
		Assert.isLegal(id != null);
		Assert.isLegal(label != null);
		this.id= id;
		this.label= label;
		this.argument= argument;
	}
	public String getId() {
		return id;
	}
	public Object getArgument() {
		return argument;
	}
	public String getLabel() {
		return label;
	}
	@Override
	public String toString() {
		if (argument == null) {
			return id;
		}
		return id + "(" + argument + ")"; //$NON-NLS-1$ //$NON-NLS-2$
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PreferenceHistoryEntry) {
			PreferenceHistoryEntry other= (PreferenceHistoryEntry) obj;
			return id.equals(other.id)
					&& (argument == null && other.argument == null
							|| argument.equals(other.argument));
		}
		return super.equals(obj);
	}
	@Override
	public int hashCode() {
		int argHash= argument == null ? 0 : argument.hashCode() & 0x0000ffff;
		return id.hashCode() << 16 | argHash;
	}
}
