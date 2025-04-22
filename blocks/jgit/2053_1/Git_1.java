package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefAlreadyExistsException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class CloneCommand implements Callable<Git> {

	private String uri;

	private File directory;

	private boolean bare;

	private String origin = Constants.DEFAULT_REMOTE_NAME;

	private String branch = Constants.MASTER;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	public Git call() throws JGitInternalException {
		try {
			URIish u = new URIish(uri);
			Repository repository = init(u);
			fetch(repository
			checkout(repository);
			return new Git(repository);
		} catch (IOException ioe) {
			throw new JGitInternalException(ioe.getMessage()
		} catch (InvalidRemoteException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (URISyntaxException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (RefAlreadyExistsException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (RefNotFoundException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (InvalidRefNameException e) {
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

	private void fetch(Repository repo
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
		command.setRefSpecs(refSpec);
		command.setProgressMonitor(monitor);
		command.call();
	}

	private void checkout(Repository repo) throws JGitInternalException
			RefAlreadyExistsException
			InvalidRefNameException {
		CheckoutCommand command = new CheckoutCommand(repo);
		command.setCreateBranch(true);
		command.setName(branch);
		command.setUpstreamMode(SetupUpstreamMode.TRACK);
		command.call();
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

}
