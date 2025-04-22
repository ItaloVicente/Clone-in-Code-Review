
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.R_TXN_COMMITTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.OK;
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
import org.eclipse.jgit.lib.TreeFormatter;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;

class Batch extends BatchRefUpdate {
	private final RefTreeDb refdb;
	private Ref src;
	private ObjectId parentId;
	private ObjectId parentTree;
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
			parentId = c;
			parentTree = c.getTree();
			tree = RefTree.readTree(rw.getObjectReader()
		} else {
			tree = RefTree.newEmptyTree();
			parentId = ObjectId.zeroId();
			parentTree = new ObjectInserter.Formatter()
					.idFor(new TreeFormatter());
		}
	}

	@Nullable
	Ref getRef(String name) throws IOException {
		return tree.exactRef(name);
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
			if (parentTree.equals(b.getTreeId())) {
				for (Command c : todo) {
					c.setResult(OK);
				}
				return;
			}
			if (!parentId.equals(ObjectId.zeroId())) {
				b.setParentId(parentId);
			}
			b.setAuthor(who);
			b.setCommitter(who);
			b.setMessage(getRefLogMessage());
			next = ins.insert(b);
			ins.flush();
		}

		RefUpdate u = refdb.newUpdate(R_TXN_COMMITTED
		u.setExpectedOldObjectId(parentId);
		u.setNewObjectId(next);
		u.setRefLogIdent(who);
		u.setRefLogMessage("commit"
		Result result = u.update(rw);
		switch (result) {
		case NEW:
		case FAST_FORWARD:
		case NO_CHANGE:
			for (Command c : todo) {
				c.setResult(OK);
			}
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
