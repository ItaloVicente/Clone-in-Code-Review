package org.eclipse.egit.ease;

import static org.eclipse.egit.ease.internal.UIText.invalidFolderLocation;
import static org.eclipse.egit.ease.internal.UIText.noRepositoryFoundAt;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.eclipse.core.resources.IContainer;
import org.eclipse.ease.modules.AbstractScriptModule;
import org.eclipse.ease.modules.ScriptParameter;
import org.eclipse.ease.modules.WrapToScript;
import org.eclipse.ease.tools.ResourceTools;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class GitModule extends AbstractScriptModule {

	@WrapToScript
	public Git clone(final String remoteLocation, final Object localLocation, @ScriptParameter(defaultValue = ScriptParameter.NULL) final String user,
			@ScriptParameter(defaultValue = ScriptParameter.NULL) final String pass, @ScriptParameter(defaultValue = ScriptParameter.NULL) final String branch)
			throws InvalidRemoteException, TransportException, GitAPIException {

		final File folder = resolveFolder(localLocation);
		if (folder == null) {
			throw new IllegalArgumentException(invalidFolderLocation + localLocation);
		}
		final CloneCommand cloneCommand = Git.cloneRepository();
		cloneCommand.setURI(remoteLocation);
		cloneCommand.setDirectory(folder);

		if ((user != null) && (pass != null)) {
			cloneCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(user, pass));
		}

		if (branch != null) {
			cloneCommand.setBranchesToClone(Arrays.asList(branch));
		}

		final Git result = cloneCommand.call();
		return result;
	}

	@WrapToScript
	public Git openRepository(final Object location) throws IOException {
		if (location instanceof Git) {
			return (Git) location;
		}

		final File folder = resolveFolder(location);
		if (folder == null) {
			throw new IllegalArgumentException(invalidFolderLocation + location);
		}
		return Git.open(folder);
	}

	@WrapToScript
	public Git initRepository(final Object location, @ScriptParameter(defaultValue = "false") final boolean bare)
			throws IllegalStateException, GitAPIException {
		final File folder = resolveFolder(location);
		if (folder == null) {
			throw new IllegalArgumentException(invalidFolderLocation + location);
		}

		if (!folder.exists()) {
			folder.mkdirs();
		}

		return Git.init().setDirectory(folder).setBare(bare).call();
	}

	@WrapToScript
	public RevCommit commit(final Object repository, final String message, final String author, @ScriptParameter(defaultValue = "false") final boolean amend)
			throws IOException, GitAPIException {
		final Git repo = openRepository(repository);
		if (repo == null) {
			throw new IllegalArgumentException(noRepositoryFoundAt + repository);
		}

		String authorName = ""; //$NON-NLS-1$
		String authorEmail = ""; //$NON-NLS-1$
		if (author != null) {
			final String[] authorTokens = author.split("|"); //$NON-NLS-1$
			if (authorTokens.length > 0) {
				authorName = authorTokens[0].trim();
			}

			if (authorTokens.length > 1) {
				authorEmail = authorTokens[1].trim();
			}
		}

		return repo.commit().setMessage(message).setAuthor(authorName, authorEmail).setAmend(amend).call();
	}

	@WrapToScript
	public DirCache add(final Object repository, final String filepattern) throws IOException, GitAPIException {
		final Git repo = openRepository(repository);
		if (repo == null) {
			throw new IllegalArgumentException(noRepositoryFoundAt + repository);
		}
		return repo.add().addFilepattern(filepattern).call();
	}

	@WrapToScript
	public Status getStatus(final Object repository) throws IOException, GitAPIException {
		final Git repo = openRepository(repository);
		if (repo == null) {
			throw new IllegalArgumentException(noRepositoryFoundAt + repository);
		}
		return repo.status().call();
	}

	@WrapToScript
	public Iterable<PushResult> push(final Object repository) throws IOException, GitAPIException {
		final Git repo = openRepository(repository);
		if (repo == null) {
			throw new IllegalArgumentException(noRepositoryFoundAt + repository);
		}
		return repo.push().call();
	}

	@WrapToScript
	public PullResult pull(final Object repository) throws IOException, GitAPIException {
		final Git repo = openRepository(repository);
		if (repo == null) {
			throw new IllegalArgumentException(noRepositoryFoundAt + repository);
		}
		return repo.pull().call();
	}

	private final File resolveFolder(final Object location) {
		Object folder = ResourceTools.resolveFolder(location, getScriptEngine(), false);
		if (folder instanceof IContainer) {
			folder = ((IContainer) folder).getRawLocation().makeAbsolute().toFile();
		}

		if ((folder instanceof File) && (!((File) folder).exists() || (((File) folder).isDirectory()))) {
			return (File) folder;
		}

		return null;
	}
}
