package org.eclipse.egit.ui.internal.synchronize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SyncRepoEntity {

	public static class SyncRefEntity {
		private final String descr;

		private final String value;

		public SyncRefEntity(String descr, String value) {
			this.descr = descr;
			this.value = value;
		}

		public String getDescription() {
			return descr;
		}

		public String getValue() {
			return value;
		}
	}

	private final String name;

	private final List<SyncRefEntity> refs;

	public SyncRepoEntity(String name) {
		this.name = name;
		refs = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void addRef(SyncRefEntity ref) {
		refs.add(ref);
	}

	public List<SyncRefEntity> getRefList() {
		return Collections.unmodifiableList(refs);
	}

}
