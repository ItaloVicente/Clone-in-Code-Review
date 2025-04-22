
package org.eclipse.jgit.pgm.debug;

import static org.eclipse.jgit.lib.Constants.HEAD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.internal.storage.reftree.RefTree;
import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.pgm.Command;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.revwalk.RevWalk;
import org.kohsuke.args4j.Option;

@Command(usage = "usage_RebuildRefTree")
class RebuildRefTree extends TextBuiltin {
	@Option(name = "--enable"
	boolean enable;

	private String txnNamespace;
	private String txnCommitted;

	@Override
	protected void run() throws Exception {
		try (ObjectReader reader = db.newObjectReader();
				RevWalk rw = new RevWalk(reader);
				ObjectInserter inserter = db.newObjectInserter()) {
			RefDatabase refDb = db.getRefDatabase();
			if (refDb instanceof RefTreeDatabase) {
				RefTreeDatabase d = (RefTreeDatabase) refDb;
				refDb = d.getBootstrap();
				txnNamespace = d.getTxnNamespace();
				txnCommitted = d.getTxnCommitted();
			} else {
				RefTreeDatabase d = new RefTreeDatabase(db
				txnNamespace = d.getTxnNamespace();
				txnCommitted = d.getTxnCommitted();
			}

			errw.format("Rebuilding %s from %s"
					txnCommitted
			errw.println();
			errw.flush();

			CommitBuilder b = new CommitBuilder();
			Ref ref = refDb.exactRef(txnCommitted);
			RefUpdate update = refDb.newUpdate(txnCommitted
			ObjectId oldTreeId;

			if (ref != null && ref.getObjectId() != null) {
				ObjectId oldId = ref.getObjectId();
				update.setExpectedOldObjectId(oldId);
				b.setParentId(oldId);
				oldTreeId = rw.parseCommit(oldId).getTree();
			} else {
				update.setExpectedOldObjectId(ObjectId.zeroId());
				oldTreeId = ObjectId.zeroId();
			}

			RefTree tree = rebuild(refDb);
			b.setTreeId(tree.writeTree(inserter));
			b.setAuthor(new PersonIdent(db));
			b.setCommitter(b.getAuthor());
			if (b.getTreeId().equals(oldTreeId)) {
				return;
			}

			update.setNewObjectId(inserter.insert(b));
			inserter.flush();

			RefUpdate.Result result = update.update(rw);
			switch (result) {
			case NEW:
			case FAST_FORWARD:
				break;
			default:
				throw die(String.format("%s: %s"
			}

			if (enable && !(db.getRefDatabase() instanceof RefTreeDatabase)) {
				StoredConfig cfg = db.getConfig();
				cfg.setInt("core"
				cfg.setString("extensions"
				cfg.save();
				errw.flush();
			}
		}
	}

	private RefTree rebuild(RefDatabase refdb) throws IOException {
		RefTree tree = RefTree.newEmptyTree();
		List<org.eclipse.jgit.internal.storage.reftree.Command> cmds
			= new ArrayList<>();

		Ref head = refdb.exactRef(HEAD);
		if (head != null) {
			cmds.add(new org.eclipse.jgit.internal.storage.reftree.Command(
					null
					head));
		}

		for (Ref r : refdb.getRefs()) {
			if (r.getName().equals(txnCommitted) || r.getName().equals(HEAD)
					|| r.getName().startsWith(txnNamespace)) {
				continue;
			}
			cmds.add(new org.eclipse.jgit.internal.storage.reftree.Command(
					null
					db.getRefDatabase().peel(r)));
		}
		tree.apply(cmds);
		return tree;
	}
}
