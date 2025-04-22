package org.eclipse.jgit.api;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.FetchConnection;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;

public class LsRemoteCommand extends
		TransportCommand<LsRemoteCommand

	private String remote = Constants.DEFAULT_REMOTE_NAME;

	private boolean heads;

	private boolean tags;

	private String uploadPack;

	public LsRemoteCommand(Repository repo) {
		super(repo);
	}

	public LsRemoteCommand setRemote(String remote) {
		checkCallable();
		this.remote = remote;
		return this;
	}

	public LsRemoteCommand setHeads(boolean heads) {
		this.heads = heads;
		return this;
	}

	public LsRemoteCommand setTags(boolean tags) {
		this.tags = tags;
		return this;
	}

	public LsRemoteCommand setUploadPack(String uploadPack) {
		this.uploadPack = uploadPack;
		return this;
	}

	@Override
	public Collection<Ref> call() throws GitAPIException
			InvalidRemoteException
			org.eclipse.jgit.api.errors.TransportException {
		return execute().values();
	}

	public Map<String
			InvalidRemoteException
			org.eclipse.jgit.api.errors.TransportException {
		return Collections.unmodifiableMap(execute());
	}

	private Map<String
			InvalidRemoteException
			org.eclipse.jgit.api.errors.TransportException {
		checkCallable();

		try (Transport transport = repo != null
				? Transport.open(repo
				: Transport.open(new URIish(remote))) {
			transport.setOptionUploadPack(uploadPack);
			configure(transport);
			Collection<RefSpec> refSpecs = new ArrayList<>(1);
			if (tags)
				refSpecs.add(new RefSpec(
			if (heads)
			Collection<Ref> refs;
			Map<String
			try (FetchConnection fc = transport.openFetch()) {
				refs = fc.getRefs();
				if (refSpecs.isEmpty())
					for (Ref r : refs)
						refmap.put(r.getName()
				else
					for (Ref r : refs)
						for (RefSpec rs : refSpecs)
							if (rs.matchSource(r)) {
								refmap.put(r.getName()
								break;
							}
				return refmap;
			}
		} catch (URISyntaxException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote
		} catch (NotSupportedException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfLsRemoteCommand
					e);
		} catch (TransportException e) {
			throw new org.eclipse.jgit.api.errors.TransportException(
					e.getMessage()
					e);
		}
	}

}
