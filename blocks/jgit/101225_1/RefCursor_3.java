
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.util.RefList;

class ListRefCursor extends RefCursor {
	private final RefList<Ref> list;

	private int index;
	private Ref ref;
	private String seeking;

	ListRefCursor(Collection<Ref> refs) {
		RefList.Builder<Ref> b = new RefList.Builder<>(refs.size());
		for (Ref r : refs) {
			b.add(r);
		}
		b.sort();
		list = b.toRefList();
	}

	@Override
	public void seekToFirstRef() throws IOException {
		index = 0;
		seeking = null;
		ref = null;
	}

	@Override
	public void seek(String refName) throws IOException {
		seeking = refName;
		ref = null;

			refName += '\1';
		}

		index = list.find(refName);
		if (index < 0) {
			index = -(index + 1);
		}
	}

	@Override
	public boolean next() throws IOException {
		if (index >= list.size()) {
			return false;
		}

		ref = list.get(index++);
		if (seeking == null) {
			return true;
		}

		String name = ref.getName();
				? name.startsWith(seeking)
				: name.equals(seeking);
	}

	@Override
	public Ref getRef() {
		return ref;
	}

	@Override
	public void close() {
	}
}
