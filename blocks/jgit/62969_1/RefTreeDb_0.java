
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.R_TXN_COMMITTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;

class Batch extends BatchRefUpdate {
	private final RefTreeDb refdb;
	private Ref src;
	private ObjectId oldId;
	private RefTree tree;

	Batch(RefTreeDb refdb) {
		super(refdb);
		this.refdb = refdb;
	}

	@Override
	public void execute(RevWalk rw
			throws IOException {
		List<Command> todo = new ArrayList<>(getCommands().size());
		for (ReceiveCommand c : getCommands()) {
			todo.add(new Command(rw
		}
		init(rw);
		execute(rw
	}

	void init(RevWalk rw) throws IOException {
		src = refdb.exactRef(R_TXN_COMMITTED);
		if (src != null && src.getObjectId() != null) {
			RevCommit c = rw.parseCommit(src.getObjectId());
			tree = RefTree.read(rw.getObjectReader()
			oldId = c;
		} else {
			tree = RefTree.newEmptyTree();
			oldId = ObjectId.zeroId();
		}
	}

	@Nullable
	Ref getRef(String name) throws IOException {
		return tree.getRef(name);
	}

	void execute(RevWalk rw
		if (!tree.apply(todo)) {
			return;
		}

		Repository repo = refdb.getRepository();
		PersonIdent who = getRefLogIdent();
		if (who == null) {
			who = new PersonIdent(repo);
		}

		ObjectId next;
		try (ObjectInserter ins = repo.newObjectInserter()) {
			CommitBuilder b = new CommitBuilder();
			b.setTreeId(tree.writeTree(ins));
			if (!oldId.equals(ObjectId.zeroId())) {
				b.setParentId(oldId);
			}
			b.setAuthor(who);
			b.setCommitter(who);
			b.setMessage(getRefLogMessage());
			next = ins.insert(b);
			ins.flush();
		}

		RefUpdate u = refdb.newUpdate(R_TXN_COMMITTED
		u.setExpectedOldObjectId(oldId);
		u.setNewObjectId(next);
		u.setRefLogIdent(who);
		u.setRefLogMessage("commit"
		Result result = u.update(rw);
		switch (result) {
		case NEW:
		case FAST_FORWARD:
		case NO_CHANGE:
			refdb.refresh();
			break;

		default:
			reject(todo
			break;
		}
	}

	private void reject(List<Command> cmds
		for (Command c : cmds) {
			c.setResult(REJECTED_OTHER_REASON
			msg = JGitText.get().transactionAborted;
		}
	}
}
