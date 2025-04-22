
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;
import static org.eclipse.jgit.util.Paths.stripTrailingSeparator;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Ref.Storage;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefRename;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.SymbolicRef;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.util.RefList;
import org.eclipse.jgit.util.RefMap;

public class RefTreeDatabase extends RefDatabase {
	private final Repository repo;
	private final RefDatabase bootstrap;
	private final String txnCommitted;
	private final String txnNamespace;
	private volatile Scanner.Result refs;

	public RefTreeDatabase(Repository repo
			String txnNamespace
		this.repo = repo;
		this.bootstrap = bootstrap;
		this.txnCommitted = txnCommitted;

		if (txnNamespace == null || txnNamespace.isEmpty()) {
			this.txnNamespace = null;
		} else {
			this.txnNamespace = stripTrailingSeparator(txnNamespace) + '/';
		}
	}

	Repository getRepository() {
		return repo;
	}

	public RefDatabase getBootstrap() {
		return bootstrap;
	}

	public String getTxnCommitted() {
		return txnCommitted;
	}

	@Nullable
	public String getTxnNamespace() {
		return txnNamespace;
	}

	@Override
	public void create() throws IOException {
		bootstrap.create();
	}

	@Override
	public boolean performsAtomicTransactions() {
		return true;
	}

	@Override
	public void refresh() {
		bootstrap.refresh();
	}

	@Override
	public void close() {
		refs = null;
		bootstrap.close();
	}

	@Override
	public Ref getRef(String name) throws IOException {
		return findRef(getRefs(ALL)
	}

	@Override
	public Ref exactRef(String name) throws IOException {
		if (conflictsWithBootstrap(name)) {
			return null;
		}

		boolean partial = false;
		Ref src = bootstrap.exactRef(txnCommitted);
		Scanner.Result c = refs;
		if (c == null || !c.refTreeId.equals(idOf(src))) {
			c = Scanner.scanRefTree(repo
			partial = true;
		}

		Ref r = c.all.get(name);
		if (r != null && r.isSymbolic()) {
			r = c.sym.get(name);
			if (partial && r.getObjectId() == null) {
				return getRefs(ALL).get(name);
			}
		}
		return r;
	}

	private static String prefixOf(String name) {
		int s = name.lastIndexOf('/');
		if (s >= 0) {
			return name.substring(0
		}
	}

	@Override
	public Map<String
		if (!prefix.isEmpty() && prefix.charAt(prefix.length() - 1) != '/') {
			return new HashMap<>(0);
		}

		Ref src = bootstrap.exactRef(txnCommitted);
		Scanner.Result c = refs;
		if (c == null || !c.refTreeId.equals(idOf(src))) {
			c = Scanner.scanRefTree(repo
			if (prefix.isEmpty()) {
				refs = c;
			}
		}

		RefList<Ref> empty = RefList.emptyList();
		return new RefMap(prefix
	}

	private static ObjectId idOf(@Nullable Ref src) {
		return src != null && src.getObjectId() != null
				? src.getObjectId()
				: ObjectId.zeroId();
	}

	@Override
	public List<Ref> getAdditionalRefs() throws IOException {
		return Collections.emptyList();
	}

	@Override
	public Ref peel(Ref ref) throws IOException {
		Ref i = ref.getLeaf();
		ObjectId id = i.getObjectId();
		if (i.isPeeled() || id == null) {
			return ref;
		}
		try (RevWalk rw = new RevWalk(repo)) {
			RevObject obj = rw.parseAny(id);
			if (obj instanceof RevTag) {
				ObjectId p = rw.peel(obj).copy();
				i = new ObjectIdRef.PeeledTag(PACKED
			} else {
				i = new ObjectIdRef.PeeledNonTag(PACKED
			}
		}
		return recreate(ref
	}

	private static Ref recreate(Ref old
		if (old.isSymbolic()) {
			Ref dst = recreate(old.getTarget()
			return new SymbolicRef(old.getName()
		}
		return leaf;
	}

	@Override
	public boolean isNameConflicting(String name) throws IOException {
		return conflictsWithBootstrap(name)
				|| !getConflictingNames(name).isEmpty();
	}

	@Override
	public BatchRefUpdate newBatchUpdate() {
		return new RefTreeBatch(this);
	}

	@Override
	public RefUpdate newUpdate(String name
		if (conflictsWithBootstrap(name)) {
			return new AlwaysFailUpdate(this
		}

		Ref r = exactRef(name);
		if (r == null) {
			r = new ObjectIdRef.Unpeeled(Storage.NEW
		}

		boolean detaching = detach && r.isSymbolic();
		if (detaching) {
			r = new ObjectIdRef.Unpeeled(LOOSE
		}

		RefTreeUpdate u = new RefTreeUpdate(this
		if (detaching) {
			u.setDetachingSymbolicRef();
		}
		return u;
	}

	@Override
	public RefRename newRename(String fromName
			throws IOException {
		RefUpdate from = newUpdate(fromName
		RefUpdate to = newUpdate(toName
		return new RefTreeRename(this
	}

	boolean conflictsWithBootstrap(String name) {
		if (txnNamespace != null && name.startsWith(txnNamespace)) {
			return true;
		} else if (txnCommitted.equals(name)) {
			return true;
		} else if (name.length() > txnCommitted.length()
				&& name.charAt(txnCommitted.length()) == '/'
				&& name.startsWith(txnCommitted)) {
			return true;
		}
		return false;
	}
}
