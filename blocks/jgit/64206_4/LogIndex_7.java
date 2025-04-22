
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchReplica.CommitMethod.ALL_REFS;
import static org.eclipse.jgit.internal.ketch.KetchReplica.CommitMethod.TXN_COMMITTED;
import static org.eclipse.jgit.lib.RefDatabase.ALL;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.OK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.internal.storage.reftree.RefTreeDatabase;
import org.eclipse.jgit.lib.BatchRefUpdate;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefDatabase;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.ReceiveCommand;

public class LocalReplica extends KetchReplica {
	public LocalReplica(KetchLeader leader
		super(leader
	}

	@Override
	protected String describeForLog() {
		return String.format("%s (leader)"
	}

	protected void initialize(Repository repo) throws IOException {
		RefDatabase refdb = repo.getRefDatabase();
		if (refdb instanceof RefTreeDatabase) {
			RefTreeDatabase treeDb = (RefTreeDatabase) refdb;
			String txnNamespace = getSystem().getTxnNamespace();
			if (!txnNamespace.equals(treeDb.getTxnNamespace())) {
				throw new IOException(MessageFormat.format(
						KetchText.get().mismatchedTxnNamespace
						txnNamespace
			}
			refdb = treeDb.getBootstrap();
		}
		initialize(refdb.exactRef(
				getSystem().getTxnAccepted()
				getSystem().getTxnCommitted()));
	}

	@Override
	protected void startPush(final ReplicaPushRequest req) {
		getSystem().getExecutor().execute(new Runnable() {
			@Override
			public void run() {
				try (Repository git = getLeader().openRepository()) {
					try {
						update(git
						req.done(git);
					} catch (Throwable err) {
						req.setException(git
					}
				} catch (IOException err) {
					req.setException(null
				}
			}
		});
	}

	private void update(Repository git
			throws IOException {
		RefDatabase refdb = git.getRefDatabase();
		CommitMethod method = getCommitMethod();

		if (refdb instanceof RefTreeDatabase) {
			if (!isOnlyTxnNamespace(req.getCommands())) {
				return;
			}

			refdb = ((RefTreeDatabase) refdb).getBootstrap();
			method = TXN_COMMITTED;
		}

		BatchRefUpdate batch = refdb.newBatchUpdate();
		batch.setRefLogIdent(getSystem().newCommitter());
		batch.setRefLogMessage("ketch"
		batch.setAllowNonFastForwards(true);

		ReceiveCommand accepted = null;
		ReceiveCommand committed = null;
		for (ReceiveCommand cmd : req.getCommands()) {
			if (getSystem().getTxnAccepted().equals(cmd.getRefName())) {
				accepted = cmd;
			} else if (getSystem().getTxnCommitted().equals(cmd.getRefName())) {
				committed = cmd;
			} else {
				batch.addCommand(cmd);
			}
		}
		if (committed != null && method == ALL_REFS) {
			Map<String
			batch.addCommand(prepareCommit(git
		}
		if (accepted != null) {
			batch.addCommand(accepted);
		}
		if (committed != null) {
			batch.addCommand(committed);
		}

		try (RevWalk rw = new RevWalk(git)) {
			batch.execute(rw
		}

		List<String> failed = new ArrayList<>(2);
		checkFailed(failed
		checkFailed(failed
		if (!failed.isEmpty()) {
			String[] arr = failed.toArray(new String[failed.size()]);
			req.setRefs(refdb.exactRef(arr));
		}
	}

	private static void checkFailed(List<String> failed
		if (cmd != null && cmd.getResult() != OK) {
			failed.add(cmd.getRefName());
		}
	}

	private boolean isOnlyTxnNamespace(Collection<ReceiveCommand> cmdList) {

		String txnNamespace = getSystem().getTxnNamespace();
		for (ReceiveCommand cmd : cmdList) {
			if (!cmd.getRefName().startsWith(txnNamespace)) {
				cmd.setResult(REJECTED_OTHER_REASON
						MessageFormat.format(
								KetchText.get().outsideTxnNamespace
								cmd.getRefName()
				ReceiveCommand.abort(cmdList);
				return false;
			}
		}
		return true;
	}
}
