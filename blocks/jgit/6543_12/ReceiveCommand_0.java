
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.errors.UnpackException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.SubscribeCommand.Command;
import org.eclipse.jgit.util.RefTranslator;

public class BasePackSubscribeConnection extends BasePackConnection implements
		SubscribeConnection {

	private static class ReceivePublishedPack extends BaseReceivePack {
		final String remote;

		private ReceivePublishedPack(Repository into
			super(into);
			remote = remoteName;
		}

		private void receive(InputStream input) throws IOException {
			init(input
			try {
				execute();
			} finally {
				unlockPack();
			}
		}

		private void execute() throws IOException {
			recvCommands();
			if (hasCommands()) {
				for (ReceiveCommand c : getAllCommands())
					c.setRefName(RefTranslator.getPubSubRefFromRemote(
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

	public void subscribe(Subscriber subscriber
			Map<String
			PrintWriter output)
			throws InterruptedException
		try {
			writeSubscribeHeader(subscriber);

			for (Map.Entry<String
					subscribeCommands.entrySet()) {
				String repoName = e.getKey();
				writeSubscribeCommands(repoName

				List<String> subscribeSpecs = new ArrayList<String>();
				for (SubscribeCommand cmd : e.getValue()) {
					if (cmd.getCommand() == Command.SUBSCRIBE)
						subscribeSpecs.add(cmd.getSpec());
				}
				writeRepositoryState(
						subscriber.getRepository(repoName)
				pckOut.end();
			}

			write("done");

			if (!readResponseHeader())
				return;

			while (!closed) {
				if (Thread.interrupted())
					throw new InterruptedException();
				readUpdate(subscriber
			}
		} finally {
			close();
		}
	}

	private void readUpdate(Subscriber subscriber
			throws TransportException
		String line = pckIn.readString();
		if (line.equals("heartbeat"))
			return;
		if (line.startsWith("restart-token ")) {
			subscriber.setRestartToken(line.substring(
					"restart-token ".length()));
			return;
		}
		if (line.startsWith("heartbeat-interval ")) {
			transport.setTimeout(Integer.parseInt(line.substring(
					"heartbeat-interval ".length())) + LATENCY_TIMEOUT);
			return;
		}
		if (!line.startsWith("update "))
			throw new TransportException(MessageFormat.format(
					JGitText.get().expectedGot
		String repo = line.substring("update ".length());

		SubscribedRepository db = subscriber.getRepository(repo);
		if (db == null)
			throw new TransportException(MessageFormat.format(
					JGitText.get().repositoryNotFound
		receivePublish(db
		line = pckIn.readString();
		if (!line.startsWith("pack-number "))
			throw new TransportException(MessageFormat.format(
					JGitText.get().expectedGot
		subscriber.setLastPackNumber(line.substring(
				"pack-number ".length()));
	}

	private void writeRepositoryState(
			SubscribedRepository repository
			throws IOException {
		for (Map.Entry<String
				repository.getPubSubRefs().entrySet()) {
			String refName = ref.getKey();
			for (String spec : subscribeSpecs) {
				if ((RefSpec.isWildcard(spec) && refName.startsWith(
						spec.substring(0
						|| refName.equals(spec))
					write("have "
							+ ref.getValue().getLeaf().getObjectId().getName()
							+ " " + refName);
			}
		}
	}

	private void writeSubscribeCommands(
			String repoName
			throws IOException {
		write("repository " + repoName);
		for (SubscribeCommand cmd : commands) {
			switch (cmd.getCommand()) {
			case SUBSCRIBE:
				write("want " + cmd.getSpec());
				break;
			case UNSUBSCRIBE:
				write("stop " + cmd.getSpec());
				break;
			}
		}
	}

	private void writeSubscribeHeader(Subscriber subscriber)
			throws IOException {
		write("subscribe");
		String restart = subscriber.getRestartToken();
		if (restart != null) {
			write("restart " + restart);
			String number = subscriber.getLastPackNumber();
			if (number != null)
				write("last-pack-number " + number);
		}
		pckOut.end();
	}

	public void sendSubscribeAdvertisement(Subscriber subscriber)
			throws IOException
		try {
			pckOut.writeString("advertisement");
			for (String repository : subscriber.getAllRepositories()) {
				write("repositoryaccess " + repository);
			}
			pckOut.end();

			readResponseHeader();
		} finally {
			close();
		}
	}

	private boolean readResponseHeader()
			throws IOException
		String line = pckIn.readString();
		if ("reconnect".equals(line))
			return false;
		if (line.startsWith("ERR "))
			throw new TransportException(line.substring("ERR ".length()));
		if (line.startsWith("error: "))
			throw new TransportException(line.substring("error: ".length()));
		if (!line.equals("ACK"))
			throw new TransportException(MessageFormat.format(
					JGitText.get().expectedGot
		return true;
	}

	private void write(String line) throws IOException {
		pckOut.writeString(line + "\n");
	}

	private void receivePublish(SubscribedRepository sr
			throws IOException {
		Repository repository = sr.getRepository();
		ReceivePublishedPack rp = new ReceivePublishedPack(
				repository
		rp.setExpectDataAfterPackFooter(true);
		rp.setAdvertisedRefs(sr.getPubSubRefs()
		try {
			rp.receive(in);
			for (ReceiveCommand rc : rp.getAllCommands()) {
				if (rc.getResult() != ReceiveCommand.Result.OK)
					throw new TransportException(rc.getMessage() + " " + rc);
			}
			String workDir = JGitText.get().notFound;
			try {
				workDir = repository.getWorkTree().getCanonicalPath();
			} catch (NoWorkTreeException e) {
			}
			output.println(MessageFormat.format(
					JGitText.get().subscribeNewUpdate
					workDir));
			for (ReceiveCommand rc : rp.getAllCommands())
				output.format("%-10s %-7s -> %-7s %s\n"
						rc.getOldId().abbreviate(7).name()
						rc.getNewId().abbreviate(7).name()
			output.flush();
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
