
package org.eclipse.jgit.internal.storage.reftree;

import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.R_TXN;
import static org.eclipse.jgit.internal.storage.reftree.RefTreeDb.R_TXN_COMMITTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
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
		List<ReceiveCommand> forStore = new ArrayList<>();
		for (ReceiveCommand c : getCommands()) {
			if (c.getRefName().startsWith(R_TXN)) {
				forStore.add(c);
			} else {
				forTree.add(new Command(rw
			}
		}

		ReceiveCommand commit = null;
		if (!forTree.isEmpty()) {
			init(rw);
			if (!apply(forTree)) {
				reject(JGitText.get().transactionAborted);
				return;
			} else if (nextId != null) {
				commit = new ReceiveCommand(parentId
				forStore.add(commit);
			}
		}

		if (!forStore.isEmpty()) {
			BatchRefUpdate u = newBootstrapBatch();
			u.addCommand(forStore);
			u.execute(rw
			if (commit != null) {
				if (commit.getResult() == OK) {
					for (Command c : forTree) {
						c.setResult(OK);
					}
				} else {
					String msg = commit.getMessage();
					if (msg != null) {
						msg = commit.getResult().name();
					}
					reject(msg);
				}
			}
		}
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
	Ref exactRef(String name) throws IOException {
		return tree.exactRef(name);
	}

	void execute(RevWalk rw
		if (apply(todo) && nextId != null) {
			commit(rw);
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

	private void commit(RevWalk rw) throws IOException {
		RefUpdate u = refdb.newUpdate(R_TXN_COMMITTED
		u.setExpectedOldObjectId(parentId);
		u.setNewObjectId(nextId);
		u.setRefLogIdent(author);
		u.setRefLogMessage("commit"
		Result result = u.update(rw);
		switch (result) {
		case NEW:
		case FAST_FORWARD:
		case NO_CHANGE:
			for (ReceiveCommand c : getCommands()) {
				c.setResult(OK);
			}
			break;

		default:
			reject(result.name());
			break;
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
}
