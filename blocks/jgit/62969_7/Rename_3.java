
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.BootstrapBehavior.HIDDEN_REJECT;
import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.BootstrapBehavior.UNION;
import static org.eclipse.jgit.lib.Ref.Storage.LOOSE;
import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

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

public class RefTreeDb extends RefDatabase {

	public enum BootstrapBehavior {
		UNION

		HIDDEN_REJECT

		HIDDEN;
	}

	private final Repository repo;
	private final RefDatabase bootstrap;
	private final BootstrapBehavior behavior;
	private volatile Scanner.Result refs;

	public RefTreeDb(Repository repo
			BootstrapBehavior behavior) {
		this.repo = repo;
		this.bootstrap = bootstrap;
		this.behavior = behavior;
	}

	Repository getRepository() {
		return repo;
	}

	public BootstrapBehavior getBehavior() {
		return behavior;
	}

	public RefDatabase getBootstrap() {
		return bootstrap;
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
		if (behavior == UNION && name.startsWith(R_TXN)) {
			return bootstrap.exactRef(name);
		}

		boolean partial = false;
		Ref src = bootstrap.exactRef(R_TXN_COMMITTED);
		Scanner.Result c = refs;
		if (c == null || !c.id.equals(idOf(src))) {
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
		if (behavior == UNION && prefix.startsWith(R_TXN)) {
			return bootstrap.getRefs(prefix);
		}
		if (!prefix.isEmpty() && prefix.charAt(prefix.length() - 1) != '/') {
			return new HashMap<>(0);
		}

		RefList<Ref> txn;
		Ref src;
		if (behavior == UNION && prefix.isEmpty()) {
			txn = listRefsTxn();
			src = txn.get(R_TXN_COMMITTED);
		} else {
			txn = RefList.emptyList();
			src = bootstrap.exactRef(R_TXN_COMMITTED);
		}

		Scanner.Result c = refs;
		if (c == null || !c.id.equals(idOf(src))) {
			c = Scanner.scanRefTree(repo
			if (prefix.isEmpty()) {
				refs = c;
			}
		}
		return new RefMap(prefix
	}

	private static ObjectId idOf(@Nullable Ref src) {
		return src != null && src.getObjectId() != null
				? src.getObjectId()
				: ObjectId.zeroId();
	}

	private RefList<Ref> listRefsTxn() throws IOException {
		RefList.Builder<Ref> txn = new RefList.Builder<>();
		for (Ref r : bootstrap.getRefs(R_TXN).values()) {
			txn.add(r);
		}
		txn.sort();
		return txn.toRefList();
	}

	@Override
	public List<Ref> getAdditionalRefs() throws IOException {
		if (behavior == UNION) {
			return bootstrap.getAdditionalRefs();
		}
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
		if (behavior == UNION && name.startsWith(R_TXN)) {
			return bootstrap.isNameConflicting(name);
		}
		return !getConflictingNames(name).isEmpty();
	}

	@Override
	public BatchRefUpdate newBatchUpdate() {
		return new Batch(this);
	}

	@Override
	public RefUpdate newUpdate(String name
		if (name.startsWith(R_TXN)) {
			if (behavior == UNION) {
				return bootstrap.newUpdate(name
			} else if (behavior == HIDDEN_REJECT) {
				return new FailUpdate(this
			}
		}

		Ref r = exactRef(name);
		if (r == null) {
			r = new ObjectIdRef.Unpeeled(Storage.NEW
		}

		boolean detaching = detach && r.isSymbolic();
		if (detaching) {
			r = new ObjectIdRef.Unpeeled(LOOSE
		}

		Update u = new Update(this
		if (detaching) {
			u.setDetachingSymbolicRef();
		}
		return u;
	}

	@Override
	public RefRename newRename(String fromName
			throws IOException {
		if (behavior == UNION && fromName.startsWith(R_TXN)
				&& toName.startsWith(R_TXN)) {
			return bootstrap.newRename(fromName
		}

		RefUpdate from = newUpdate(fromName
		RefUpdate to = newUpdate(toName
		return new Rename(this
	}
}
