
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.lib.Constants.OBJ_TREE;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.OK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_NONFASTFORWARD;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;
import static org.eclipse.jgit.transport.ReceiveCommand.Type.UPDATE;
import static org.eclipse.jgit.transport.ReceiveCommand.Type.UPDATE_NONFASTFORWARD;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;

class RefTreeBatch extends BatchRefUpdate {
	private final RefTreeDatabase refdb;
	private Ref src;
	private ObjectId parentCommitId;
	private ObjectId parentTreeId;
	private RefTree tree;
	private PersonIdent author;
	private ObjectId newCommitId;

	RefTreeBatch(RefTreeDatabase refdb) {
		super(refdb);
		this.refdb = refdb;
	}

	@Override
	public void execute(RevWalk rw
			throws IOException {
		List<Command> todo = new ArrayList<>(getCommands().size());
		for (ReceiveCommand c : getCommands()) {
			if (!isAllowNonFastForwards()) {
				if (c.getType() == UPDATE) {
					c.updateType(rw);
				}
				if (c.getType() == UPDATE_NONFASTFORWARD) {
					c.setResult(REJECTED_NONFASTFORWARD);
					if (isAtomic()) {
						ReceiveCommand.abort(getCommands());
						return;
					} else {
						continue;
					}
				}
			}
			todo.add(new Command(rw
		}
		init(rw);
		execute(rw
	}

	void init(RevWalk rw) throws IOException {
		src = refdb.getBootstrap().exactRef(refdb.getTxnCommitted());
		if (src != null && src.getObjectId() != null) {
			RevCommit c = rw.parseCommit(src.getObjectId());
			parentCommitId = c;
			parentTreeId = c.getTree();
			tree = RefTree.read(rw.getObjectReader()
		} else {
			parentCommitId = ObjectId.zeroId();
			parentTreeId = new ObjectInserter.Formatter()
					.idFor(OBJ_TREE
			tree = RefTree.newEmptyTree();
		}
	}

	@Nullable
	Ref exactRef(ObjectReader reader
		return tree.exactRef(reader
	}

	void execute(RevWalk rw
		for (Command c : todo) {
			if (c.getResult() != NOT_ATTEMPTED) {
				Command.abort(todo
				return;
			}
			if (refdb.conflictsWithBootstrap(c.getRefName())) {
				c.setResult(REJECTED_OTHER_REASON
						.format(JGitText.get().invalidRefName
				Command.abort(todo
				return;
			}
		}

		if (apply(todo) && newCommitId != null) {
			commit(rw
		}
	}

	private boolean apply(List<Command> todo) throws IOException {
		if (!tree.apply(todo)) {
			return false;
		}

		Repository repo = refdb.getRepository();
		try (ObjectInserter ins = repo.newObjectInserter()) {
			CommitBuilder b = new CommitBuilder();
			b.setTreeId(tree.writeTree(ins));
			if (parentTreeId.equals(b.getTreeId())) {
				for (Command c : todo) {
					c.setResult(OK);
				}
				return true;
			}
			if (!parentCommitId.equals(ObjectId.zeroId())) {
				b.setParentId(parentCommitId);
			}

			author = getRefLogIdent();
			if (author == null) {
				author = new PersonIdent(repo);
			}
			b.setAuthor(author);
			b.setCommitter(author);
			b.setMessage(getRefLogMessage());
			newCommitId = ins.insert(b);
			ins.flush();
		}
		return true;
	}

	private void commit(RevWalk rw
		ReceiveCommand commit = new ReceiveCommand(
				parentCommitId
				refdb.getTxnCommitted());
		updateBootstrap(rw

		if (commit.getResult() == OK) {
			for (Command c : todo) {
				c.setResult(OK);
			}
		} else {
			Command.abort(todo
		}
	}

	private void updateBootstrap(RevWalk rw
			throws IOException {
		BatchRefUpdate u = refdb.getBootstrap().newBatchUpdate();
		u.setAllowNonFastForwards(true);
		u.setPushCertificate(getPushCertificate());
		if (isRefLogDisabled()) {
			u.disableRefLog();
		} else {
			u.setRefLogIdent(author);
			u.setRefLogMessage(getRefLogMessage()
		}
		u.addCommand(commit);
		u.execute(rw
	}
}
