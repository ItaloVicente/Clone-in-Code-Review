package org.eclipse.jgit.niofs.internal.op.commands;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkInstanceOf;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.GitImpl;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;

public class Push {

	private final GitImpl git;
	private final CredentialsProvider credentialsProvider;
	private final Map.Entry<String
	private final boolean force;
	private final Collection<RefSpec> refSpecs;

	public Push(final Git git
			final boolean force
		this.git = checkInstanceOf("git"
		this.credentialsProvider = credentialsProvider;
		this.remote = checkNotNull("remote"
		this.force = force;
		this.refSpecs = refSpecs;
	}

	public void execute() throws InvalidRemoteException {
		try {
			final List<RefSpec> specs = new UpdateRemoteConfig(git
			git._push().setCredentialsProvider(credentialsProvider).setRefSpecs(specs).setRemote(remote.getKey())
					.setForce(force).setPushAll().call();
		} catch (final InvalidRemoteException e) {
			throw e;
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
