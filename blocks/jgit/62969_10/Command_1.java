
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.R_TXN;
import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.R_TXN_COMMITTED;
import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.BootstrapBehavior.HIDDEN_REJECT;
import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.BootstrapBehavior.UNION;
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
import org.eclipse.jgit.internal.storage.reftree.RefTreeDb.BootstrapBehavior;
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

class Batch extends BatchRefUpdate {
	private final RefTreeDb refdb;
	private Ref src;
	private ObjectId parentId;
	private ObjectId parentTree;
	private RefTree tree;
	private PersonIdent author;
	private ObjectId nextId;

	Batch(RefTreeDb refdb) {
		super(refdb);
		this.refdb = refdb;
	}

	@Override
	public void execute(RevWalk rw
			throws IOException {
		List<Command> forTree = new ArrayList<>(getCommands().size());
		List<ReceiveCommand> forBootstrap = new ArrayList<>();
		boolean hasRefsTxnCommitted = false;
		BootstrapBehavior behavior = refdb.getBehavior();

		for (ReceiveCommand c : getCommands()) {
			if (c.getResult() != NOT_ATTEMPTED) {
				reject(JGitText.get().transactionAborted);
				return;
			}

			if (behavior == HIDDEN_REJECT && isBootstrapRef(c)) {
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

			if (behavior == UNION && isBootstrapRef(c)) {
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
			} else if (nextId != null) {
				commit = new ReceiveCommand(parentId
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
	private static boolean isBootstrapRef(ReceiveCommand c) {
		return c.getRefName().startsWith(R_TXN);
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
			parentId = c;
			parentTree = c.getTree();
			tree = RefTree.read(rw.getObjectReader()
		} else {
			tree = RefTree.newEmptyTree();
			parentId = ObjectId.zeroId();
			parentTree = new ObjectInserter.Formatter()
					.idFor(OBJ_TREE
		}
	}

	@Nullable
	Ref exactRef(ObjectReader reader
		return tree.exactRef(reader
	}

	void execute(RevWalk rw
		if (apply(todo) && nextId != null) {
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
			if (parentTree.equals(b.getTreeId())) {
				for (Command c : todo) {
					c.setResult(OK);
				}
				return true;
			}
			if (!parentId.equals(ObjectId.zeroId())) {
				b.setParentId(parentId);
			}

			author = getRefLogIdent();
			if (author == null) {
				author = new PersonIdent(repo);
			}
			b.setAuthor(author);
			b.setCommitter(author);
			b.setMessage(getRefLogMessage());
			nextId = ins.insert(b);
			ins.flush();
		}
		return true;
	}

	private void commit(RevWalk rw
		RefUpdate u = refdb.getBootstrap().newUpdate(R_TXN_COMMITTED
		u.setExpectedOldObjectId(parentId);
		u.setNewObjectId(nextId);
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
			String msg = result.name();
			for (Command c : todo) {
				if (c.getResult() == NOT_ATTEMPTED) {
					c.setResult(REJECTED_OTHER_REASON
					msg = JGitText.get().transactionAborted;
				}
			}
			break;
		}
	}
}
