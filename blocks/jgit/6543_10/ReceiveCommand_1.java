
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.errors.UnpackException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.SubscribeCommand.Command;

public class BasePackSubscribeConnection extends BasePackConnection implements
		SubscribeConnection {

	class ReceivePublishedPack extends BaseReceivePack {
		final String remote;

		ReceivePublishedPack(Repository into
			super(into);
			remote = remoteName;
		}

		void receive(InputStream input) throws IOException {
			init(input
			try {
				execute();
			} finally {
				unlockPack();
			}
		}

		void execute() throws IOException {
			recvCommands();
			if (hasCommands()) {
				for (ReceiveCommand c : getAllCommands())
					c.setName(SubscribedRepository.getPubSubRefFromRemote(
							remote
				Throwable unpackError = null;
				if (needPack()) {
					try {
						receivePackAndCheckConnectivity();
					} catch (IOException err) {
						unpackError = err;
					} catch (RuntimeException err) {
						unpackError = err;
					} catch (Error err) {
						unpackError = err;
					}
				}
				if (unpackError == null) {
					validateCommands();
					executeCommands();
				}
				unlockPack();

				if (unpackError != null)
					throw new UnpackException(unpackError);
			}
		}

		@Override
		protected String getLockMessageProcessName() {
			return "pubsub-receive-" + remote;
		}
	}

	private volatile boolean closed;

	BasePackSubscribeConnection(PackTransport packTransport) {
		super(packTransport);
	}

	protected void start(InputStream myIn
		closed = false;
		init(myIn
	}

	public void doSubscribe(Subscriber subscriber
			Map<String
			ProgressMonitor monitor)
			throws InterruptedException
		try {
			monitor.beginTask(MessageFormat.format(
					JGitText.get().subscribeStart

			String restart = subscriber.getRestartToken();
			if (restart != null) {
				write("restart " + restart);
				String number = subscriber.getLastPackNumber();
				if (number != null)
					write("last-pack " + number);
			}
			pckOut.end();

			for (Map.Entry<String
					subscribeCommands.entrySet()) {
				List<SubscribeCommand> subscribeOnly = new ArrayList<
						SubscribeCommand>();
				String repoName = e.getKey();
				write("repository " + repoName);
				for (SubscribeCommand cmd : e.getValue()) {
					if (cmd.getCommand() == Command.SUBSCRIBE) {
						write("want " + cmd.getSpec());
						subscribeOnly.add(cmd);
					} else
						write("stop " + cmd.getSpec());
				}
				SubscribedRepository repo = subscriber.getRepository(repoName);
				for (Map.Entry<String
						repo.getPubSubRefs().entrySet()) {
					String refName = ref.getKey();
					for (SubscribeCommand cmd : subscribeOnly) {
						String spec = cmd.getSpec();
								spec.substring(0
								|| refName.equals(spec))
							write("have " + ref.getValue()
									.getLeaf().getObjectId().getName() + " "
									+ refName);
					}
				}
				pckOut.end();
			}

			write("done");
			monitor.update(1);

			if (!readResponseHeader(subscriber))
				return;

			monitor.update(1);
			monitor.endTask();

			while (!closed) {
				if (Thread.interrupted() || monitor.isCancelled())
					throw new InterruptedException();

				String line = pckIn.readString();
				if (line.equals("heartbeat"))
					continue;
				if (line.startsWith("change-restart-token ")) {
					subscriber.setRestartToken(line.substring(
							"change-restart-token ".length()));
					continue;
				}
				if (!line.startsWith("update "))
					throw new TransportException(MessageFormat.format(
							JGitText.get().expectedGot
				String repo = line.substring("update ".length());

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
				subscriber.setLastPackNumber(line.substring(
						"sequence ".length()));
			}
		} finally {
			close();
		}
	}

	public void doSubscribeAdvertisement(Subscriber subscriber)
			throws IOException {
		try {
			pckOut.end();
			for (String repository : subscriber.getAllRepositories()) {
				write("repository " + repository);
				pckOut.end();
			}
			write("done advertisement");

			readResponseHeader(subscriber);
		} finally {
			close();
		}
	}

	private boolean readResponseHeader(Subscriber subscriber)
			throws IOException
		String line = pckIn.readString();
		if ("reconnect".equals(line))
			return false;

		if (line.startsWith("error: "))
			throw new TransportException(line.substring("error: ".length()));
		if (!line.startsWith("restart-token "))
			throw new TransportException(MessageFormat.format(
					JGitText.get().expectedGot
		subscriber.setRestartToken(line.substring(
				"restart-token ".length()));
		line = pckIn.readString();

		if (!line.startsWith("heartbeat-interval "))
			throw new TransportException(MessageFormat.format(
					JGitText.get().expectedGot
					line));
		transport.setTimeout(Integer.parseInt(
				line.substring("heartbeat-interval ".length()))
				+ LATENCY_TIMEOUT);

		if ((line = pckIn.readString()) != PacketLineIn.END)
			throw new TransportException(MessageFormat.format(
					JGitText.get().expectedGot
		return true;
	}

	private void write(String line) throws IOException {
		pckOut.writeString(line + "\n");
	}

	private void receivePublish(SubscribedRepository repo)
			throws IOException {
		ReceivePublishedPack rp = new ReceivePublishedPack(
				repo.getRepository()
		rp.setExpectDataAfterPackFooter(true);
		rp.setAdvertisedRefs(repo.getPubSubRefs()
		try {
			rp.receive(in);
			for (ReceiveCommand rc : rp.getAllCommands()) {
				if (rc.getResult() != ReceiveCommand.Result.OK)
					throw new TransportException(rc.getMessage() + " " + rc);
			}
		} finally {
			rp.release();
		}
	}

	@Override
	public void close() {
		closed = true;
		super.close();
	}
}
