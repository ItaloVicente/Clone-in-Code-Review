
package org.eclipse.jgit.internal.ketch;

import static org.eclipse.jgit.internal.ketch.KetchReplica.CommitMethod.ALL_REFS;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.LOCK_FAILURE;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.OK;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_NODELETE;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_NONFASTFORWARD;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushConnection;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;

public class RemoteGitReplica extends KetchReplica {
	private final URIish uri;
	private final RemoteConfig remoteConfig;

	public RemoteGitReplica(KetchLeader leader
			ReplicaConfig cfg
		super(leader
		this.uri = uri;
		this.remoteConfig = rc;
	}

	public URIish getURI() {
		return uri;
	}

	@Nullable
	protected RemoteConfig getRemoteConfig() {
		return remoteConfig;
	}

	@Override
	protected String describeForLog() {
		return String.format("%s @ %s"
	}

	@Override
	protected void start(final ReplicaPushRequest req) {
		getSystem().getExecutor().execute(new Runnable() {
			@Override
			public void run() {
				try (Repository git = getLeader().openRepository()) {
					try {
						push(git
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

	private void push(Repository repo
			throws NotSupportedException
		Map<String
		List<RemoteCommand> cmds = asUpdateList(req.getCommands());
		Transport tn = Transport.open(repo
		try {
			RemoteConfig rc = getRemoteConfig();
			if (rc != null) {
				tn.applyConfig(rc);
			}
			tn.setPushAtomic(true);
			adv = push(repo
		} finally {
			tn.close();
		}
		for (RemoteCommand c : cmds) {
			c.copyStatusToResult();
		}
		req.setRefs(adv);
	}

	private Map<String
			List<RemoteCommand> cmds) throws NotSupportedException
					TransportException
		Map<String
		PushConnection connection = tn.openPush();
		try {
			Map<String
			RemoteRefUpdate accepted = updates.get(getSystem().getTxnAccepted());
			if (accepted != null && !check(adv
				reject(cmds);
				return adv;
			}

			RemoteRefUpdate committed = updates.get(getSystem().getTxnCommitted());
			if (committed != null && !check(adv
				reject(cmds);
				return adv;
			}
			if (committed != null && getCommitMethod() == ALL_REFS) {
				commit(git
			}

			connection.push(NullProgressMonitor.INSTANCE
			return adv;
		} finally {
			connection.close();
		}
	}

	private static boolean check(Map<String
		Ref r = adv.get(u.getRemoteName());
		if (!AnyObjectId.equals(getId(r)
			((RemoteCommand) u).cmd.setResult(LOCK_FAILURE);
			return false;
		}
		return true;
	}

	private static void reject(List<RemoteCommand> cmds) {
		for (RemoteCommand cmd : cmds) {
			if (cmd.cmd.getResult() == NOT_ATTEMPTED) {
				cmd.cmd.setResult(
					REJECTED_OTHER_REASON
					JGitText.get().transactionAborted);
			}
		}
	}

	private void commit(Repository git
			Map<String
			ObjectId committed) throws IOException {
		for (ReceiveCommand cmd : commit(git
			RemoteCommand c = new RemoteCommand(cmd);
			cmds.add(c);
			updates.put(c.getRemoteName()
		}
	}

	private static List<RemoteCommand> asUpdateList(
			Collection<ReceiveCommand> cmds) {
		try {
			List<RemoteCommand> toPush = new ArrayList<>(cmds.size());
			for (ReceiveCommand cmd : cmds) {
				toPush.add(new RemoteCommand(cmd));
			}
			return toPush;
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	private static Map<String
			List<RemoteCommand> cmds) {
		Map<String
		for (RemoteCommand c : cmds) {
			m.put(c.getRemoteName()
		}
		return m;
	}

	static class RemoteCommand extends RemoteRefUpdate {
		final ReceiveCommand cmd;

		RemoteCommand(ReceiveCommand cmd) throws IOException {
			super(null
					cmd.getNewId()
					cmd.getOldId());
			this.cmd = cmd;
		}

		void copyStatusToResult() {
			if (cmd.getResult() == NOT_ATTEMPTED) {
				switch (getStatus()) {
				case OK:
				case UP_TO_DATE:
				case NON_EXISTING:
					cmd.setResult(OK);
					break;

				case REJECTED_NODELETE:
					cmd.setResult(REJECTED_NODELETE);
					break;

				case REJECTED_NONFASTFORWARD:
					cmd.setResult(REJECTED_NONFASTFORWARD);
					break;

				case REJECTED_OTHER_REASON:
					cmd.setResult(REJECTED_OTHER_REASON
					break;

				default:
					cmd.setResult(REJECTED_OTHER_REASON
					break;
				}
			}
		}
	}
}
