
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.SubscribeCommand.Command;

public class BasePackSubscribeConnection extends BasePackConnection implements
		SubscribeConnection {
	private volatile boolean closed;

	BasePackSubscribeConnection(PackTransport packTransport) {
		super(packTransport);
	}

	public void doSubscribe(Subscriber subscriber
			Map<String
			ProgressMonitor monitor)
			throws InterruptedException
		try {
			monitor.beginTask(MessageFormat.format(
					JGitText.get().subscribeStart

			String restart = subscriber.getRestartToken();
			if (restart == null)
				pckOut.writeString("hello");
			else {
				String sequence = subscriber.getLastPackNumber();
				if (sequence == null)
					pckOut.writeString("fast-restart " + restart);
				else
					pckOut.writeString(
							"fast-restart " + restart + " " + sequence);
			}

			for (Map.Entry<String
					subscribeCommands.entrySet()) {
				pckOut.writeString("repo " + e.getKey());
				for (SubscribeCommand cmd : e.getValue()) {
					if (cmd.getCommand() == Command.SUBSCRIBE)
						pckOut.writeString("subscribe " + cmd.getSpec());
					else
						pckOut.writeString("unsubscribe " + cmd.getSpec());
				}
			}
			pckOut.end();

			for (String repoName : subscribeCommands.keySet()) {
				SubscribedRepository repo = subscriber.getRepository(repoName);
				pckOut.writeString("repo " + repoName);
				for (Map.Entry<String
						repo.getPubSubRefs().entrySet()) {
					pckOut.writeString(ref.getValue().getLeaf().getObjectId()
							.getName() + " " + ref.getKey());
				}
			}
			pckOut.end();
			monitor.update(1);

			String line = pckIn.readString();
			String parts[] = line.split(" "
			if (parts[0].equals("reconnect")) {
				subscriber.setRestartToken(null);
				subscriber.setLastPackNumber(null);
				return;
			}
			if (!parts[0].equals("fast-restart"))
				throw new TransportException(MessageFormat.format(
						JGitText.get().expectedGot
			subscriber.setRestartToken(parts[1]);
			if ((line = pckIn.readString()) != PacketLineIn.END)
				throw new TransportException(MessageFormat.format(
						JGitText.get().expectedGot
			monitor.update(1);
			monitor.endTask();

			while (!closed) {
				line = pckIn.readString();
				if (line.equals("heartbeat")) {
					if ((line = pckIn.readString()) != PacketLineIn.END)
						throw new TransportException(MessageFormat.format(
								JGitText.get().expectedGot
					continue;
				} else if (!line.startsWith("update "))
					throw new TransportException(MessageFormat.format(
							JGitText.get().expectedGot
				String repo = line.split(" "

				SubscribedRepository db = subscriber.getRepository(repo);
				if (db == null)
					throw new TransportException(MessageFormat.format(
							JGitText.get().repositoryNotFound
				monitor.beginTask(MessageFormat.format(
						JGitText.get().subscribeNewUpdate
				receivePublish(db);
				monitor.update(1);
				monitor.endTask();

				line = pckIn.readString();
				if (!line.startsWith("sequence "))
					throw new TransportException(MessageFormat.format(
							JGitText.get().expectedGot
				subscriber.setLastPackNumber(line.split(" "
				if ((line = pckIn.readString()) != PacketLineIn.END)
					throw new TransportException(MessageFormat.format(
							JGitText.get().expectedGot

				if (Thread.interrupted() || monitor.isCancelled())
					throw new InterruptedException();
			}
		} finally {
			close();
		}
	}

	private void receivePublish(SubscribedRepository repo)
			throws IOException {
		final String remote = repo.getRemote();
		ReceivePack rp = new ReceivePack(repo.getRepository()) {
			@Override
			protected void validateCommands() {
				for (ReceiveCommand c : getAllCommands())
					c.setName(SubscribedRepository.getPubSubRefFromLocal(
							remote
				super.validateCommands();
			}
		};
		rp.setExpectDataAfterPackFooter(true);
		rp.setBiDirectionalPipe(false);
		rp.setAdvertisedRefs(repo.getPubSubRefs()
		try {
			rp.receive(in
		} finally {
			rp.unlockPack();
		}
	}

	public void close() {
		closed = true;
		super.close();
	}
}
