
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchConstants.ACCEPTED;
import static org.eclipse.jgit.internal.ketch.KetchConstants.COMMITTED;
import static org.eclipse.jgit.internal.ketch.KetchReplica.CommitMethod.ALL_REFS;
import static org.eclipse.jgit.internal.ketch.KetchReplica.CommitMethod.TXN_COMMITTED;
import static org.eclipse.jgit.lib.RefDatabase.ALL;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.OK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.internal.JGitText;
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
		String txnNamespace = getTxnNamespace();
		RefDatabase refdb = repo.getRefDatabase();
		if (refdb instanceof RefTreeDatabase) {
			RefTreeDatabase treeDb = (RefTreeDatabase) refdb;
			if (!txnNamespace.equals(treeDb.getTxnNamespace())) {
				throw new IOException(MessageFormat.format(
						KetchText.get().mismatchedTxnNamespace
						txnNamespace
			}
			refdb = treeDb.getBootstrap();
		}
		initialize(refdb.exactRef(
				txnNamespace + ACCEPTED
				txnNamespace + COMMITTED));
	}

	@Override
	protected void start(final ReplicaPushRequest req) {
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

	private void update(Repository repo
			throws IOException {
		RefDatabase refdb = repo.getRefDatabase();
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

		String acceptedRefName = getTxnNamespace() + ACCEPTED;
		String committedRefName = getTxnNamespace() + COMMITTED;

		ReceiveCommand accepted = null;
		ReceiveCommand committed = null;
		for (ReceiveCommand cmd : req.getCommands()) {
			if (acceptedRefName.equals(cmd.getRefName())) {
				accepted = cmd;
			} else if (committedRefName.equals(cmd.getRefName())) {
				committed = cmd;
			} else {
				batch.addCommand(cmd);
			}
		}
		if (committed != null && method == ALL_REFS) {
			Map<String
			batch.addCommand(commit(repo
		}
		if (accepted != null) {
			batch.addCommand(accepted);
		}
		if (committed != null) {
			batch.addCommand(committed);
		}

		try (RevWalk rw = new RevWalk(repo)) {
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

		String txnNamespace = getTxnNamespace();
		for (ReceiveCommand cmd : cmdList) {
			if (!cmd.getRefName().startsWith(txnNamespace)) {
				cmd.setResult(REJECTED_OTHER_REASON
						MessageFormat.format(
								KetchText.get().outsideTxnNamespace
								cmd.getRefName()
				abort(cmdList);
				return false;
			}
		}
		return true;
	}

	private static void abort(Collection<ReceiveCommand> cmdList) {
		for (ReceiveCommand c : cmdList) {
			if (c.getResult() == NOT_ATTEMPTED) {
				c.setResult(REJECTED_OTHER_REASON
						JGitText.get().transactionAborted);
			}
		}
	}
}
