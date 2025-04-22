
package org.eclipse.jgit.lib;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Map;

import org.eclipse.jgit.internal.storage.file.RefDirectory;
import org.eclipse.jgit.util.RefList;
import org.eclipse.jgit.util.RefMap;

public abstract class RefWriter {

	private final Collection<Ref> refs;

	public RefWriter(Collection<Ref> refs) {
		this.refs = RefComparator.sort(refs);
	}

	public RefWriter(Map<String
		if (refs instanceof RefMap)
			this.refs = refs.values();
		else
			this.refs = RefComparator.sort(refs.values());
	}

	public RefWriter(RefList<Ref> refs) {
		this.refs = refs.asList();
	}

	public void writeInfoRefs() throws IOException {
		final StringWriter w = new StringWriter();
		final char[] tmp = new char[Constants.OBJECT_ID_STRING_LENGTH];
		for (Ref r : refs) {
			if (Constants.HEAD.equals(r.getName())) {
				continue;
			}

			ObjectId objectId = r.getObjectId();
			if (objectId == null) {
				continue;
			}
			objectId.copyTo(tmp
			w.write('\t');
			w.write(r.getName());
			w.write('\n');

			ObjectId peeledObjectId = r.getPeeledObjectId();
			if (peeledObjectId != null) {
				peeledObjectId.copyTo(tmp
				w.write('\t');
				w.write(r.getName());
			}
		}
		writeFile(Constants.INFO_REFS
	}

	public void writePackedRefs() throws IOException {
		boolean peeled = false;
		for (Ref r : refs) {
			if (r.getStorage().isPacked() && r.isPeeled()) {
				peeled = true;
				break;
			}
		}

		final StringWriter w = new StringWriter();
		if (peeled) {
			w.write(RefDirectory.PACKED_REFS_HEADER);
			if (peeled)
				w.write(RefDirectory.PACKED_REFS_PEELED);
			w.write('\n');
		}

		final char[] tmp = new char[Constants.OBJECT_ID_STRING_LENGTH];
		for (Ref r : refs) {
			if (r.getStorage() != Ref.Storage.PACKED)
				continue;

			ObjectId objectId = r.getObjectId();
			if (objectId == null) {
				throw new NullPointerException();
			}
			objectId.copyTo(tmp
			w.write(' ');
			w.write(r.getName());
			w.write('\n');

			ObjectId peeledObjectId = r.getPeeledObjectId();
			if (peeledObjectId != null) {
				w.write('^');
				peeledObjectId.copyTo(tmp
				w.write('\n');
			}
		}
		writeFile(Constants.PACKED_REFS
	}

	protected abstract void writeFile(String file
			throws IOException;
}
