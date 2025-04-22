package org.eclipse.jgit.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TooLargeObjectInPackException;
import org.eclipse.jgit.errors.TooLargePackException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefLeaseSpec;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.Transport;

public class PushCommand extends
		TransportCommand<PushCommand

	private String remote = Constants.DEFAULT_REMOTE_NAME;

	private final List<RefSpec> refSpecs;

	private final Map<String

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private String receivePack = RemoteConfig.DEFAULT_RECEIVE_PACK;

	private boolean dryRun;
	private boolean atomic;
	private boolean force;
	private boolean thin = Transport.DEFAULT_PUSH_THIN;

	private OutputStream out;

	private List<String> pushOptions;

	protected PushCommand(Repository repo) {
		super(repo);
		refSpecs = new ArrayList<>(3);
		refLeaseSpecs = new HashMap<>();
	}

	@Override
	public Iterable<PushResult> call() throws GitAPIException
			InvalidRemoteException
			org.eclipse.jgit.api.errors.TransportException {
		checkCallable();

		ArrayList<PushResult> pushResults = new ArrayList<>(3);

		try {
			if (refSpecs.isEmpty()) {
				RemoteConfig config = new RemoteConfig(repo.getConfig()
						getRemote());
				refSpecs.addAll(config.getPushRefSpecs());
			}
			if (refSpecs.isEmpty()) {
				Ref head = repo.exactRef(Constants.HEAD);
				if (head != null && head.isSymbolic())
					refSpecs.add(new RefSpec(head.getLeaf().getName()));
			}

			if (force) {
				for (int i = 0; i < refSpecs.size(); i++)
					refSpecs.set(i
			}

			final List<Transport> transports;
			transports = Transport.openAll(repo
					final Transport transport : transports) {
				transport.setPushThin(thin);
				transport.setPushAtomic(atomic);
				if (receivePack != null)
					transport.setOptionReceivePack(receivePack);
				transport.setDryRun(dryRun);
				transport.setPushOptions(pushOptions);
				configure(transport);

				final Collection<RemoteRefUpdate> toPush = transport
						.findRemoteRefUpdatesFor(refSpecs

				try {
					PushResult result = transport.push(monitor
					pushResults.add(result);

				} catch (TooLargePackException e) {
					throw new org.eclipse.jgit.api.errors.TooLargePackException(
							e.getMessage()
				} catch (TooLargeObjectInPackException e) {
					throw new org.eclipse.jgit.api.errors.TooLargeObjectInPackException(
							e.getMessage()
				} catch (TransportException e) {
					throw new org.eclipse.jgit.api.errors.TransportException(
							e.getMessage()
				} finally {
					transport.close();
				}
			}

		} catch (URISyntaxException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote
		} catch (TransportException e) {
			throw new org.eclipse.jgit.api.errors.TransportException(
					e.getMessage()
		} catch (NotSupportedException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfPushCommand
					e);
		} catch (IOException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfPushCommand
					e);
		}

		return pushResults;
	}

	public PushCommand setRemote(String remote) {
		checkCallable();
		this.remote = remote;
		return this;
	}

	public String getRemote() {
		return remote;
	}

	public PushCommand setReceivePack(String receivePack) {
		checkCallable();
		this.receivePack = receivePack;
		return this;
	}

	public String getReceivePack() {
		return receivePack;
	}

	public int getTimeout() {
		return timeout;
	}

	public ProgressMonitor getProgressMonitor() {
		return monitor;
	}

	public PushCommand setProgressMonitor(ProgressMonitor monitor) {
		checkCallable();
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}

	public List<RefLeaseSpec> getRefLeaseSpecs() {
		return new ArrayList<>(refLeaseSpecs.values());
	}

	public PushCommand setRefLeaseSpecs(RefLeaseSpec... specs) {
		return setRefLeaseSpecs(Arrays.asList(specs));
	}

	public PushCommand setRefLeaseSpecs(List<RefLeaseSpec> specs) {
		checkCallable();
		this.refLeaseSpecs.clear();
		for (RefLeaseSpec spec : specs) {
			refLeaseSpecs.put(spec.getRef()
		}
		return this;
	}

	public List<RefSpec> getRefSpecs() {
		return refSpecs;
	}

	public PushCommand setRefSpecs(RefSpec... specs) {
		checkCallable();
		this.refSpecs.clear();
		Collections.addAll(refSpecs
		return this;
	}

	public PushCommand setRefSpecs(List<RefSpec> specs) {
		checkCallable();
		this.refSpecs.clear();
		this.refSpecs.addAll(specs);
		return this;
	}

	public PushCommand setPushAll() {
		refSpecs.add(Transport.REFSPEC_PUSH_ALL);
		return this;
	}

	public PushCommand setPushTags() {
		refSpecs.add(Transport.REFSPEC_TAGS);
		return this;
	}

	public PushCommand add(Ref ref) {
		refSpecs.add(new RefSpec(ref.getLeaf().getName()));
		return this;
	}

	public PushCommand add(String nameOrSpec) {
		if (0 <= nameOrSpec.indexOf(':')) {
			refSpecs.add(new RefSpec(nameOrSpec));
		} else {
			Ref src;
			try {
				src = repo.findRef(nameOrSpec);
			} catch (IOException e) {
				throw new JGitInternalException(
						JGitText.get().exceptionCaughtDuringExecutionOfPushCommand
						e);
			}
			if (src != null)
				add(src);
		}
		return this;
	}

	public boolean isDryRun() {
		return dryRun;
	}

	public PushCommand setDryRun(boolean dryRun) {
		checkCallable();
		this.dryRun = dryRun;
		return this;
	}

	public boolean isThin() {
		return thin;
	}

	public PushCommand setThin(boolean thin) {
		checkCallable();
		this.thin = thin;
		return this;
	}

	public boolean isAtomic() {
		return atomic;
	}

	public PushCommand setAtomic(boolean atomic) {
		checkCallable();
		this.atomic = atomic;
		return this;
	}

	public boolean isForce() {
		return force;
	}

	public PushCommand setForce(boolean force) {
		checkCallable();
		this.force = force;
		return this;
	}

	public PushCommand setOutputStream(OutputStream out) {
		this.out = out;
		return this;
	}

	public List<String> getPushOptions() {
		return pushOptions;
	}

	public PushCommand setPushOptions(List<String> pushOptions) {
		this.pushOptions = pushOptions;
		return this;
	}
}
