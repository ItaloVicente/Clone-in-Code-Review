package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class CloneCommand implements Callable<Git> {

	private String uri;

	private File directory;

	private boolean bare;

	private String origin = Constants.DEFAULT_REMOTE_NAME;

	private String branch = Constants.HEAD;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private CredentialsProvider credentialsProvider;

	public Git call() throws JGitInternalException {
		try {
			URIish u = new URIish(uri);
			Repository repository = init(u);
			FetchResult result = fetch(repository
			checkout(repository
			return new Git(repository);
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		} catch (InvalidRemoteException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (URISyntaxException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	private Repository init(URIish u) {
		InitCommand command = Git.init();
		command.setBare(bare);
		if (directory == null)
			directory = new File(u.getHumanishName()
		command.setDirectory(directory);
		return command.call().getRepository();
	}

	private FetchResult fetch(Repository repo
			throws URISyntaxException
			JGitInternalException
			InvalidRemoteException
		RemoteConfig config = new RemoteConfig(repo.getConfig()
		config.addURI(u);

		final String dst = Constants.R_REMOTES + config.getName();
		RefSpec refSpec = new RefSpec();
		refSpec = refSpec.setForceUpdate(true);
		refSpec = refSpec.setSourceDestination(Constants.R_HEADS + "*"

		config.addFetchRefSpec(refSpec);
		config.update(repo.getConfig());
		repo.getConfig().save();

		FetchCommand command = new FetchCommand(repo);
		command.setRemote(origin);
		command.setProgressMonitor(monitor);
		if (credentialsProvider != null)
			command.setCredentialsProvider(credentialsProvider);
		return command.call();
	}

	private void checkout(Repository repo
			throws JGitInternalException
			MissingObjectException

		if (branch.startsWith(Constants.R_HEADS)) {
			final RefUpdate head = repo.updateRef(Constants.HEAD);
			head.disableRefLog();
			head.link(branch);
		}

		final Ref head = result.getAdvertisedRef(branch);
		if (head == null || head.getObjectId() == null)

		final RevCommit commit = parseCommit(repo

		boolean detached = !head.getName().startsWith(Constants.R_HEADS);
		RefUpdate u = repo.updateRef(Constants.HEAD
		u.setNewObjectId(commit.getId());
		u.forceUpdate();

		DirCache dc = repo.lockDirCache();
		DirCacheCheckout co = new DirCacheCheckout(repo
		co.checkout();
	}

	private RevCommit parseCommit(final Repository repo
			throws MissingObjectException
			IOException {
		final RevWalk rw = new RevWalk(repo);
		final RevCommit commit;
		try {
			commit = rw.parseCommit(ref.getObjectId());
		} finally {
			rw.release();
		}
		return commit;
	}

	public CloneCommand setURI(String uri) {
		this.uri = uri;
		return this;
	}

	public CloneCommand setDirectory(File directory) {
		this.directory = directory;
		return this;
	}

	public CloneCommand setBare(boolean bare) {
		this.bare = bare;
		return this;
	}

	public CloneCommand setOrigin(String origin) {
		this.origin = origin;
		return this;
	}

	public CloneCommand setBranch(String branch) {
		this.branch = branch;
		return this;
	}

	public CloneCommand setProgressMonitor(ProgressMonitor monitor) {
		this.monitor = monitor;
		return this;
	}

	public CloneCommand setCredentialsProvider(
			CredentialsProvider credentialsProvider) {
		this.credentialsProvider = credentialsProvider;
		return this;
	}

}
