
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.Ref.Storage.PACKED;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectReader;
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
	static final int MAX_SYMREF_DEPTH = MAX_SYMBOLIC_REF_DEPTH;

	private final Repository repo;
	private final RefDatabase bootstrap;
	private RefTree tree;

	public RefTreeDb(Repository repo
		this.repo = repo;
		this.bootstrap = bootstrap;
	}

	Repository getRepository() {
		return repo;
	}

	@Override
	public void create() throws IOException {
		bootstrap.create();
	}

	@Override
	public void close() {
		tree = null;
		bootstrap.close();
	}

	@Override
	public Ref getRef(String name) throws IOException {
		for (String p : SEARCH_PATH) {
			Ref r = exactRef(p + name);
			if (r != null) {
				return r;
			}
		}
		return null;
	}

	@Override
	public Ref exactRef(String name) throws IOException {
		if (name.startsWith(R_TXN)) {
			return bootstrap.exactRef(name);
		}
		return read().getRef(name);
	}

	@Override
	public Map<String
		if (prefix.startsWith(R_TXN)) {
			return bootstrap.getRefs(prefix);
		}

		RefList<Ref> txn;
		Ref src;
		if (prefix.isEmpty()) {
			txn = listRefsTxn();
			src = txn.get(R_TXN_COMMITTED);
		} else {
			txn = RefList.emptyList();
			src = bootstrap.exactRef(R_TXN_COMMITTED);
		}

		Scanner.Result refs = Scanner.scanRefTree(repo
		return new RefMap(prefix
	}

	private RefList<Ref> listRefsTxn() throws IOException {
		RefList.Builder<Ref> txn = new RefList.Builder<>();
		for (Ref r : bootstrap.getRefs(R_TXN).values()) {
			txn.add(r);
		}
		txn.sort();
		return txn.toRefList();
	}

	RefTree read() throws IOException {
		if (tree != null) {
			return tree;
		}

		Ref src = bootstrap.exactRef(R_TXN_COMMITTED);
		if (src == null || src.getObjectId() == null) {
			tree = RefTree.newEmptyTree();
			return tree;
		}

		ObjectReader reader = repo.newObjectReader();
		tree = RefTree.readTree(
				reader
				Scanner.commitToTree(reader
		return tree;
	}

	@Override
	public List<Ref> getAdditionalRefs() {
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
	public void refresh() {
		tree = null;
		bootstrap.refresh();
	}

	@Override
	public boolean isNameConflicting(String name) throws IOException {
		if (name.startsWith(R_TXN)) {
			return bootstrap.isNameConflicting(name);
		}
	}

	@Override
	public boolean performsAtomicTransactions() {
		return true;
	}

	@Override
	public BatchRefUpdate newBatchUpdate() {
		return new Batch(this);
	}

	@Override
	public RefUpdate newUpdate(String name
		if (name.startsWith(R_TXN)) {
			return bootstrap.newUpdate(name
		}

		Ref r = exactRef(name);
		if (r == null) {
			r = new ObjectIdRef.Unpeeled(Storage.NEW
		}
		return new Update(this
	}

	@Override
	public RefRename newRename(String fromName
			throws IOException {
		throw new UnsupportedOperationException();
	}
}
