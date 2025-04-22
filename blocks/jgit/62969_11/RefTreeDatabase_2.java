
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase.R_TXN;
import static org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase.R_TXN_COMMITTED;
import static org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase.Layering.REJECT_REFS_TXN;
import static org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase.Layering.SHOW_ALL;
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
import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase.Layering;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.CommitBuilder;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RefUpdate.Result;
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
		List<Command> forTree = new ArrayList<>(getCommands().size());
		List<ReceiveCommand> forBootstrap = new ArrayList<>();
		boolean hasRefsTxnCommitted = false;
		Layering behavior = refdb.getBehavior();

		for (ReceiveCommand c : getCommands()) {
			if (c.getResult() != NOT_ATTEMPTED) {
				reject(JGitText.get().transactionAborted);
				return;
			}

			if (behavior == REJECT_REFS_TXN && isBootstrapRef(c.getRefName())) {
				c.setResult(REJECTED_OTHER_REASON
						.format(JGitText.get().invalidRefName
				reject(JGitText.get().transactionAborted);
				return;
			}

			if (!isAllowNonFastForwards()) {
				if (c.getType() == UPDATE) {
					c.updateType(rw);
				}
				if (c.getType() == UPDATE_NONFASTFORWARD) {
					c.setResult(REJECTED_NONFASTFORWARD);
					reject(JGitText.get().transactionAborted);
					return;
				}
			}

			if (behavior == SHOW_ALL && isBootstrapRef(c.getRefName())) {
				hasRefsTxnCommitted |= R_TXN_COMMITTED.equals(c.getRefName());
				forBootstrap.add(c);
			} else {
				forTree.add(new Command(rw
			}
		}

		ReceiveCommand commit = null;
		if (!forTree.isEmpty()) {
			if (hasRefsTxnCommitted) {
				reject(JGitText.get().transactionAborted);
				return;
			}
			init(rw);
			if (!apply(forTree)) {
				reject(JGitText.get().transactionAborted);
				return;
			} else if (newCommitId != null) {
				commit = new ReceiveCommand(parentCommitId
						R_TXN_COMMITTED);
				forBootstrap.add(commit);
			}
		}

		if (!forBootstrap.isEmpty()) {
			BatchRefUpdate u = newBootstrapBatch();
			u.addCommand(forBootstrap);
			u.execute(rw
			if (commit != null) {
				if (commit.getResult() == OK) {
					for (Command c : forTree) {
						c.setResult(OK);
					}
				} else {
					String msg = commit.getMessage();
					if (msg == null || msg.isEmpty()) {
						msg = commit.getResult().name();
					}
					reject(msg);
				}
			}
		}
	}

	private void reject(String msg) {
		for (ReceiveCommand c : getCommands()) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(REJECTED_OTHER_REASON
				msg = JGitText.get().transactionAborted;
			}
		}
	}

	private static boolean isBootstrapRef(String refName) {
		return refName.startsWith(R_TXN);
	}

	private BatchRefUpdate newBootstrapBatch() {
		BatchRefUpdate u = refdb.getBootstrap().newBatchUpdate();
		u.setAllowNonFastForwards(isAllowNonFastForwards());
		u.setPushCertificate(getPushCertificate());
		if (isRefLogDisabled()) {
			u.disableRefLog();
		} else {
			u.setRefLogIdent(author != null ? author : getRefLogIdent());
			u.setRefLogMessage(
					getRefLogMessage()
					isRefLogIncludingResult());
		}
		return u;
	}

	void init(RevWalk rw) throws IOException {
		src = refdb.getBootstrap().exactRef(R_TXN_COMMITTED);
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
		if (refdb.getBehavior() != Layering.HIDE_REFS_TXN) {
			for (Command c : todo) {
				if (isBootstrapRef(c.getRefName())) {
					c.setResult(REJECTED_OTHER_REASON
							JGitText.get().invalidRefName
					reject(todo
					return;
				}
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
		RefUpdate u = refdb.getBootstrap().newUpdate(R_TXN_COMMITTED
		u.setExpectedOldObjectId(parentCommitId);
		u.setNewObjectId(newCommitId);
		u.setRefLogIdent(author);
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

	private static void reject(List<Command> todo
		for (Command c : todo) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(REJECTED_OTHER_REASON
				msg = JGitText.get().transactionAborted;
			}
		}
	}
}
