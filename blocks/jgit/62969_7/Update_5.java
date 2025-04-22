
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.RefDatabase.MAX_SYMBOLIC_REF_DEPTH;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.R_REFS;
import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.lib.FileMode.TYPE_GITLINK;
import static org.eclipse.jgit.lib.FileMode.TYPE_SYMLINK;
import static org.eclipse.jgit.lib.FileMode.TYPE_TREE;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

import java.io.IOException;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
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
			String prefix
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
		for (int idx = 0; idx < sym.size();) {
			Ref s = sym.get(idx);
			Ref r = resolve(s
			if (r != null) {
				sym.set(idx++
			} else {
				sym.remove(idx);
				int rm = aList.find(s.getName());
				if (0 <= rm) {
					aList = aList.remove(rm);
				}
			}
		}
		return new Result(srcId
	}

	private static void scan(ObjectReader reader
			String prefix
			RefList.Builder<Ref> all
					throws IncorrectObjectTypeException
		CanonicalTreeParser p = createParserAtPath(reader
		if (p == null) {
			return;
		}

		while (!p.eof()) {
			int mode = p.getEntryRawMode();
			if (mode == TYPE_TREE) {
				if (recursive) {
					p = p.createSubtreeIterator(reader);
				} else {
					p = p.next();
				}
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

	private static CanonicalTreeParser createParserAtPath(ObjectReader reader
			AnyObjectId srcId
		ObjectId root = toTree(reader
		if (prefix.isEmpty()) {
			return new CanonicalTreeParser(BINARY_R_REFS
		}

		String dir = prefix;
		if (dir.charAt(dir.length() - 1) == '/') {
			dir = dir.substring(0
		}
		TreeWalk tw = TreeWalk.forPath(reader
		if (tw == null || !tw.isSubtree()) {
			return null;
		}

		ObjectId id = tw.getObjectId(0);
		return new CanonicalTreeParser(encode(prefix)
	}

	private static Ref resolve(Ref ref
			throws IOException {
		if (!ref.isSymbolic()) {
			return ref;
		} else if (MAX_SYMBOLIC_REF_DEPTH <= depth) {
			return null;
		}

		Ref r = refs.get(ref.getTarget().getName());
		if (r == null) {
			return ref;
		}

		Ref dst = resolve(r
		if (dst == null) {
			return null;
		}
		return new SymbolicRef(ref.getName()
	}

	@SuppressWarnings("resource")
	private static RevTree toTree(ObjectReader reader
			throws IOException {
		return new RevWalk(reader).parseTree(id);
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
			Ref trg = new ObjectIdRef.Unpeeled(NEW
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
