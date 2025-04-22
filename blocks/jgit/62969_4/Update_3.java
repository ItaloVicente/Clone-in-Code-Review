
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.MAX_SYMREF_DEPTH;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.OBJ_COMMIT;
import static org.eclipse.jgit.lib.Constants.R_REFS;
import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.lib.FileMode.TYPE_GITLINK;
import static org.eclipse.jgit.lib.FileMode.TYPE_SYMLINK;
import static org.eclipse.jgit.lib.FileMode.TYPE_TREE;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

import java.io.IOException;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.MutableObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.RefList;

class Scanner {
	private static final int MAX_SYMLINK_BYTES = 10 << 10;
	private static final byte[] BINARY_R_REFS = encode(R_REFS);

	static class Result {
		final ObjectId id;
		final RefList<Ref> all;
		final RefList<Ref> sym;

		Result(ObjectId id
			this.id = id;
			this.all = all;
			this.sym = sym;
		}
	}

	static Result scanRefTree(Repository repo
			throws IOException
		RefList.Builder<Ref> all = new RefList.Builder<>();
		RefList.Builder<Ref> sym = new RefList.Builder<>();

		ObjectId srcId;
		if (src != null && src.getObjectId() != null) {
			try (ObjectReader reader = repo.newObjectReader()) {
				srcId = src.getObjectId();
				scan(reader
			}
		} else {
			srcId = ObjectId.zeroId();
		}

		RefList<Ref> aList = all.toRefList();
		for (int i = 0; i < sym.size(); i++) {
			sym.set(i
		}
		return new Result(srcId
	}

	private static void scan(ObjectReader reader
			RefList.Builder<Ref> all
					throws IncorrectObjectTypeException
		CanonicalTreeParser p = new CanonicalTreeParser(
				BINARY_R_REFS
				reader
				commitToTree(reader
		while (!p.eof()) {
			int mode = p.getEntryRawMode();
			if (mode == TYPE_TREE) {
				p = p.createSubtreeIterator(reader);
				continue;
			}

			if (!isPeelSuffix(p)) {
				Ref r = toRef(reader
				if (r != null) {
					all.add(r);
					if (r.isSymbolic()) {
						sym.add(r);
					}
				}
			} else if (mode == TYPE_GITLINK) {
				peel(all
			}
			p = p.next();
		}
	}

	private static Ref resolve(Ref ref
			throws IOException {
		if (ref != null && ref.isSymbolic() && depth < MAX_SYMREF_DEPTH) {
			Ref r = refs.get(ref.getTarget().getName());
			Ref dst = resolve(r
			if (dst != null) {
				return new SymbolicRef(ref.getName()
			}
		}
		return ref;
	}

	private static AnyObjectId commitToTree(ObjectReader reader
			throws IOException {
		byte[] raw = reader.open(id
		MutableObjectId idBuf = new MutableObjectId();
		idBuf.fromString(raw
		return idBuf;
	}

	private static boolean isPeelSuffix(CanonicalTreeParser t) {
		int n = t.getEntryPathLength();
		byte[] c = t.getEntryPathBuffer();
		return n > 3 && c[n - 3] == '^' && c[n - 2] == '{' && c[n - 1] == '}';
	}

	private static void peel(RefList.Builder<Ref> all
		String name = refName(p
		for (int idx = all.size() - 1; 0 <= idx; idx--) {
			Ref r = all.get(idx);
			int cmp = r.getName().compareTo(name);
			if (cmp == 0) {
				all.set(idx
						r.getObjectId()
				break;
			} else if (cmp < 0) {
				break;
			}
		}
	}

	private static Ref toRef(ObjectReader reader
			CanonicalTreeParser p) throws IOException {
		if (mode == TYPE_GITLINK) {
			String name = refName(p
			ObjectId id = p.getEntryObjectId();
			return new ObjectIdRef.PeeledNonTag(PACKED

		} else if (mode == TYPE_SYMLINK) {
			ObjectId id = p.getEntryObjectId();
			byte[] bin = reader.open(id
					.getCachedBytes(MAX_SYMLINK_BYTES);
			String dst = RawParseUtils.decode(bin);
			Ref trg = new ObjectIdRef.Unpeeled(PACKED
			String name = refName(p
			return new SymbolicRef(name
		}
		return null;
	}

	private static String refName(CanonicalTreeParser p
		byte[] buf = p.getEntryPathBuffer();
		int len = p.getEntryPathLength();
		if (peel) {
			len -= 3;
		}
		int ptr = 0;
		if (RawParseUtils.match(buf
			ptr = 7;
		}
		return RawParseUtils.decode(buf
	}

	private Scanner() {
	}
}
