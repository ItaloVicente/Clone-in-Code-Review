
package org.eclipse.jgit.pgm;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;

import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.SystemReader;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Clone extends AbstractFetchCommand implements CloneCommand.Callback {
	@Option(name = "--origin"
	private String remoteName = Constants.DEFAULT_REMOTE_NAME;

	@Option(name = "--branch"
	private String branch;

	@Option(name = "--no-checkout"
	private boolean noCheckout;

	@Option(name = "--bare"
	private boolean isBare;

	@Option(name = "--quiet"
	private Boolean quiet;

	@Option(name = "--recurse-submodules"
	private boolean cloneSubmodules;

	@Argument(index = 0
	private String sourceUri;

	@Argument(index = 1
	private String localName;

	@Override
	protected final boolean requiresRepository() {
		return false;
	}

	@Override
	protected void run() throws Exception {
		if (localName != null && gitdir != null)
			throw die(CLIText.get().conflictingUsageOf_git_dir_andArguments);

		final URIish uri = new URIish(sourceUri);
		File localNameF;
		if (localName == null) {
			try {
				localName = uri.getHumanishName();
				if (isBare) {
					localName = localName + Constants.DOT_GIT_EXT;
				}
				localNameF = new File(SystemReader.getInstance().getProperty(
						Constants.OS_USER_DIR)
			} catch (IllegalArgumentException e) {
				throw die(MessageFormat.format(
						CLIText.get().cannotGuessLocalNameFrom
			}
		} else
			localNameF = new File(localName);

		if (branch == null)
			branch = Constants.HEAD;

		CloneCommand command = Git.cloneRepository();
		command.setURI(sourceUri).setRemote(remoteName).setBare(isBare)
				.setNoCheckout(noCheckout).setBranch(branch)
				.setCloneSubmodules(cloneSubmodules);

		command.setGitDir(gitdir == null ? null : new File(gitdir));
		command.setDirectory(localNameF);
		boolean msgs = quiet == null || !quiet.booleanValue();
		if (msgs) {
			command.setProgressMonitor(new TextProgressMonitor(errw))
					.setCallback(this);
			outw.println(MessageFormat.format(
					CLIText.get().cloningInto
			outw.flush();
		}
		try {
			db = command.call().getRepository();
			if (msgs && db.resolve(Constants.HEAD) == null)
				outw.println(CLIText.get().clonedEmptyRepository);
		} catch (InvalidRemoteException e) {
			throw die(MessageFormat.format(CLIText.get().doesNotExist
					sourceUri));
		} finally {
			if (db != null)
				db.close();
		}
		if (msgs) {
			outw.println();
			outw.flush();
		}
	}

	@Override
	public void initializedSubmodules(Collection<String> submodules) {
		try {
			for (String submodule : submodules) {
				outw.println(MessageFormat
						.format(CLIText.get().submoduleRegistered
			}
			outw.flush();
		} catch (IOException e) {
		}
	}

	@Override
	public void cloningSubmodule(String path) {
		try {
			outw.println(MessageFormat.format(
					CLIText.get().cloningInto
			outw.flush();
		} catch (IOException e) {
		}
	}

	@Override
	public void checkingOut(AnyObjectId commit
		try {
			outw.println(MessageFormat.format(CLIText.get().checkingOut
					path
			outw.flush();
		} catch (IOException e) {
		}
	}
}
