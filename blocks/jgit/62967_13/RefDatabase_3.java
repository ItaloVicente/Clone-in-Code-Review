
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.eclipse.jgit.lib.Constants.R_REFS;
import static org.eclipse.jgit.lib.Constants.encode;
import static org.eclipse.jgit.lib.FileMode.GITLINK;
import static org.eclipse.jgit.lib.FileMode.SYMLINK;
import static org.eclipse.jgit.lib.FileMode.TYPE_GITLINK;
import static org.eclipse.jgit.lib.FileMode.TYPE_SYMLINK;
import static org.eclipse.jgit.lib.Ref.Storage.NEW;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.eclipse.jgit.lib.RefDatabase.MAX_SYMBOLIC_REF_DEPTH;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.LOCK_FAILURE;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.DeletePath;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.DirCacheNameConflictException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.util.RawParseUtils;

public class RefTree {

	public static RefTree newEmptyTree() {
		return new RefTree(DirCache.newInCore());
	}

	public static RefTree read(ObjectReader reader
			throws MissingObjectException
			CorruptObjectException
		return new RefTree(DirCache.read(reader
	}

	private DirCache contents;
	private Map<ObjectId

	private RefTree(DirCache dc) {
		this.contents = dc;
	}

	@Nullable
	public Ref exactRef(ObjectReader reader
		Ref r = readRef(reader
		if (r == null) {
			return null;
		} else if (r.isSymbolic()) {
			return resolve(reader
		}

		DirCacheEntry p = contents.getEntry(peeledPath(name));
		if (p != null && p.getRawMode() == TYPE_GITLINK) {
			return new ObjectIdRef.PeeledTag(PACKED
					r.getObjectId()
		}
		return r;
	}

	private Ref readRef(ObjectReader reader
		DirCacheEntry e = contents.getEntry(refPath(name));
		return e != null ? toRef(reader
	}

	private Ref toRef(ObjectReader reader
			throws IOException {
		int mode = e.getRawMode();
		if (mode == TYPE_GITLINK) {
			ObjectId id = e.getObjectId();
			return new ObjectIdRef.PeeledNonTag(PACKED
		}

		if (mode == TYPE_SYMLINK) {
			ObjectId id = e.getObjectId();
			String n = pendingBlobs != null ? pendingBlobs.get(id) : null;
			if (n == null) {
				byte[] bin = reader.open(id
				n = RawParseUtils.decode(bin);
			}
			Ref dst = new ObjectIdRef.Unpeeled(NEW
			return new SymbolicRef(name
		}

	}

	private Ref resolve(ObjectReader reader
			throws IOException {
		if (ref.isSymbolic() && depth < MAX_SYMBOLIC_REF_DEPTH) {
			Ref r = readRef(reader
			if (r == null) {
				return ref;
			}
			Ref dst = resolve(reader
			return new SymbolicRef(ref.getName()
		}
		return ref;
	}

	public boolean apply(Collection<Command> cmdList) {
		try {
			DirCacheEditor ed = contents.editor();
			for (Command cmd : cmdList) {
				apply(ed
			}
			ed.finish();
			return true;
		} catch (DirCacheNameConflictException e) {
			String r1 = refName(e.getPath1());
			String r2 = refName(e.getPath2());
			for (Command cmd : cmdList) {
				if (r1.equals(cmd.getRefName())
						|| r2.equals(cmd.getRefName())) {
					cmd.setResult(LOCK_FAILURE);
					break;
				}
			}
			return abort(cmdList);
		} catch (LockFailureException e) {
			return abort(cmdList);
		}
	}

	private void apply(DirCacheEditor ed
		String path = refPath(cmd.getRefName());
		Ref oldRef = cmd.getOldRef();
		final Ref newRef = cmd.getNewRef();

		if (newRef == null) {
			checkRef(contents.getEntry(path)
			ed.add(new DeletePath(path));
			cleanupPeeledRef(ed
			return;
		}

		if (newRef.isSymbolic()) {
			final String dst = newRef.getTarget().getName();
			ed.add(new PathEdit(path) {
				@Override
				public void apply(DirCacheEntry ent) {
					checkRef(ent
					ObjectId id = Command.symref(dst);
					ent.setFileMode(SYMLINK);
					ent.setObjectId(id);
					if (pendingBlobs == null) {
						pendingBlobs = new HashMap<>(4);
					}
					pendingBlobs.put(id
				}
			}.setReplace(false));
			cleanupPeeledRef(ed
			return;
		}

		ed.add(new PathEdit(path) {
			@Override
			public void apply(DirCacheEntry ent) {
				checkRef(ent
				ent.setFileMode(GITLINK);
				ent.setObjectId(newRef.getObjectId());
			}
		}.setReplace(false));

		if (newRef.getPeeledObjectId() != null) {
			ed.add(new PathEdit(peeledPath(newRef.getName())) {
				@Override
				public void apply(DirCacheEntry ent) {
					ent.setFileMode(GITLINK);
					ent.setObjectId(newRef.getPeeledObjectId());
				}
			}.setReplace(false));
		} else {
			cleanupPeeledRef(ed
		}
	}

	private static void checkRef(@Nullable DirCacheEntry ent
		if (!cmd.checkRef(ent)) {
			cmd.setResult(LOCK_FAILURE);
			throw new LockFailureException();
		}
	}

	private static void cleanupPeeledRef(DirCacheEditor ed
		if (ref != null && !ref.isSymbolic()
				&& (!ref.isPeeled() || ref.getPeeledObjectId() != null)) {
			ed.add(new DeletePath(peeledPath(ref.getName())));
		}
	}

	private static boolean abort(Iterable<Command> cmdList) {
		for (Command cmd : cmdList) {
			if (cmd.getResult() == NOT_ATTEMPTED) {
				reject(cmd
			}
		}
		return false;
	}

	private static void reject(Command cmd
		cmd.setResult(REJECTED_OTHER_REASON
	}

	public static String refName(String path) {
		if (path.startsWith(ROOT_DOTDOT)) {
			return path.substring(2);
		}
		return R_REFS + path;
	}

	private static String refPath(String name) {
		if (name.startsWith(R_REFS)) {
			return name.substring(R_REFS.length());
		}
		return ROOT_DOTDOT + name;
	}

	private static String peeledPath(String name) {
		return refPath(name) + PEELED_SUFFIX;
	}

	public ObjectId writeTree(ObjectInserter inserter) throws IOException {
		if (pendingBlobs != null) {
			for (String s : pendingBlobs.values()) {
				inserter.insert(OBJ_BLOB
			}
			pendingBlobs = null;
		}
		return contents.writeTree(inserter);
	}

	public RefTree copy() {
		RefTree r = new RefTree(DirCache.newInCore());
		DirCacheBuilder b = r.contents.builder();
		for (int i = 0; i < contents.getEntryCount(); i++) {
			b.add(new DirCacheEntry(contents.getEntry(i)));
		}
		b.finish();
		if (pendingBlobs != null) {
			r.pendingBlobs = new HashMap<>(pendingBlobs);
		}
		return r;
	}

	private static class LockFailureException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
}
