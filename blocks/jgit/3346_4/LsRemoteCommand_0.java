package org.eclipse.jgit.api;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.FetchConnection;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.Transport;

public class LsRemoteCommand extends GitCommand<Collection<Ref>> {

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

	public void setHeads(boolean heads) {
		this.heads = heads;
	}

	public void setTags(boolean tags) {
		this.tags = tags;
	}

	public void setUploadPack(String uploadPack) {
		this.uploadPack = uploadPack;
	}

	public Collection<Ref> call() throws Exception {
		checkCallable();

		try {
			Transport transport = Transport.open(repo
			transport.setOptionUploadPack(uploadPack);

			try {
				Collection<RefSpec> refSpecs = new ArrayList<RefSpec>(1);
				if (tags) {
					refSpecs.add(new RefSpec(
				}
				if (heads) {
					refSpecs.add(new RefSpec(
				}
				Collection<Ref> refs;
				Map<String
				FetchConnection fc = transport.openFetch();
				try {
					refs = fc.getRefs();
					for (Ref r : refs) {
						boolean found = refSpecs.isEmpty();
						for (RefSpec rs : refSpecs) {
							if (rs.matchSource(r)) {
								found = true;
								break;
							}
						}
						if (found) {
							refmap.put(r.getName()
						}

					}
				} finally {
					fc.close();
				}
				return refmap.values();

			} catch (TransportException e) {
				throw new JGitInternalException(
						JGitText.get().exceptionCaughtDuringExecutionOfLsRemoteCommand
						e);
			} finally {
				transport.close();
			}
		} catch (URISyntaxException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote
		} catch (NotSupportedException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfLsRemoteCommand
					e);
		}
	}

}
