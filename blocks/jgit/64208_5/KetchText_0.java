
package org.eclipse.jgit.internal.ketch;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.eclipse.jgit.internal.ketch.Proposal.State.EXECUTED;
import static org.eclipse.jgit.internal.ketch.Proposal.State.QUEUED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.NOT_ATTEMPTED;
import static org.eclipse.jgit.transport.ReceiveCommand.Result.REJECTED_OTHER_REASON;

import java.io.IOException;
import java.util.Collection;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.transport.PreReceiveHook;
import org.eclipse.jgit.transport.ProgressSpinner;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceivePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KetchPreReceive implements PreReceiveHook {
	private static final Logger log = LoggerFactory.getLogger(KetchPreReceive.class);

	private final KetchLeader leader;

	public KetchPreReceive(KetchLeader leader) {
		this.leader = leader;
	}

	@Override
	public void onPreReceive(ReceivePack rp
		cmds = ReceiveCommand.filter(cmds
		if (cmds.isEmpty()) {
			return;
		}

		try {
			Proposal proposal = new Proposal(rp.getRevWalk()
				.setPushCertificate(rp.getPushCertificate())
				.setAuthor(rp.getRefLogIdent())
			leader.queueProposal(proposal);
			if (proposal.isDone()) {
				return;
			}

			ProgressSpinner spinner = new ProgressSpinner(
					rp.getMessageOutputStream());
			if (proposal.getState() == QUEUED) {
				waitForQueue(proposal
			}
			if (!proposal.isDone()) {
				waitForPropose(proposal
			}
		} catch (IOException | InterruptedException e) {
			String msg = JGitText.get().transactionAborted;
			for (ReceiveCommand cmd : cmds) {
				if (cmd.getResult() == NOT_ATTEMPTED) {
					cmd.setResult(REJECTED_OTHER_REASON
				}
			}
			log.error(msg
		}
	}

	private void waitForQueue(Proposal proposal
			throws InterruptedException {
		spinner.beginTask(KetchText.get().waitingForQueue
		while (!proposal.awaitStateChange(QUEUED
			spinner.update();
		}
		switch (proposal.getState()) {
		case RUNNING:
		default:
			spinner.endTask(KetchText.get().starting);
			break;

		case EXECUTED:
			spinner.endTask(KetchText.get().accepted);
			break;

		case ABORTED:
			spinner.endTask(KetchText.get().failed);
			break;
		}
	}

	private void waitForPropose(Proposal proposal
			throws InterruptedException {
		spinner.beginTask(KetchText.get().proposingUpdates
		while (!proposal.await(250
			spinner.update();
		}
		spinner.endTask(proposal.getState() == EXECUTED
				? KetchText.get().accepted
				: KetchText.get().failed);
	}
}
