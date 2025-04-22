package org.eclipse.jgit.api;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.BranchConfig.BranchRebaseMode;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.TagOpt;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.FS;

public class CloneCommand extends TransportCommand<CloneCommand

	private String uri;

	private File directory;

	private File gitDir;

	private boolean bare;

	private FS fs;

	private String remote = Constants.DEFAULT_REMOTE_NAME;

	private String branch = Constants.HEAD;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private boolean cloneAllBranches;

	private boolean cloneSubmodules;

	private boolean noCheckout;

	private Collection<String> branchesToClone;

	private Callback callback;

	private boolean directoryExistsInitially;

	private boolean gitDirExistsInitially;

	public interface Callback {
		void initializedSubmodules(Collection<String> submodules);

		void cloningSubmodule(String path);

		void checkingOut(AnyObjectId commit
	}

	public CloneCommand() {
		super(null);
	}

	@Nullable
	File getDirectory() {
		return directory;
	}

	@Override
	public Git call() throws GitAPIException
			org.eclipse.jgit.api.errors.TransportException {
		URIish u = null;
		try {
			u = new URIish(uri);
			verifyDirectories(u);
		} catch (URISyntaxException e) {
			throw new InvalidRemoteException(
					MessageFormat.format(JGitText.get().invalidURL
		}
		Repository repository = init();
		FetchResult fetchResult = null;
		Thread cleanupHook = new Thread(() -> cleanup());
		Runtime.getRuntime().addShutdownHook(cleanupHook);
		try {
			fetchResult = fetch(repository
		} catch (IOException ioe) {
			if (repository != null) {
				repository.close();
			}
			cleanup();
			throw new JGitInternalException(ioe.getMessage()
		} catch (URISyntaxException e) {
			if (repository != null) {
				repository.close();
			}
			cleanup();
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote
		} catch (GitAPIException | RuntimeException e) {
			if (repository != null) {
				repository.close();
			}
			cleanup();
			throw e;
		} finally {
			Runtime.getRuntime().removeShutdownHook(cleanupHook);
		}
		if (!noCheckout) {
			try {
				checkout(repository
			} catch (IOException ioe) {
				repository.close();
				throw new JGitInternalException(ioe.getMessage()
			} catch (GitAPIException | RuntimeException e) {
				repository.close();
				throw e;
			}
		}
		return new Git(repository
	}

	private static boolean isNonEmptyDirectory(File dir) {
		if (dir != null && dir.exists()) {
			File[] files = dir.listFiles();
			return files != null && files.length != 0;
		}
		return false;
	}

	void verifyDirectories(URIish u) {
		if (directory == null && gitDir == null) {
		}
		directoryExistsInitially = directory != null && directory.exists();
		gitDirExistsInitially = gitDir != null && gitDir.exists();
		validateDirs(directory
		if (isNonEmptyDirectory(directory)) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().cloneNonEmptyDirectory
		}
		if (isNonEmptyDirectory(gitDir)) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().cloneNonEmptyDirectory
		}
	}

	private Repository init() throws GitAPIException {
		InitCommand command = Git.init();
		command.setBare(bare);
		if (fs != null) {
			command.setFs(fs);
		}
		if (directory != null) {
			command.setDirectory(directory);
		}
		if (gitDir != null) {
			command.setGitDir(gitDir);
		}
		return command.call().getRepository();
	}

	private FetchResult fetch(Repository clonedRepo
			throws URISyntaxException
			org.eclipse.jgit.api.errors.TransportException
			GitAPIException {
		RemoteConfig config = new RemoteConfig(clonedRepo.getConfig()
		config.addURI(u);

		final String dst = (bare ? Constants.R_HEADS : Constants.R_REMOTES
				+ config.getName() + '/') + '*';
		boolean fetchAll = cloneAllBranches || branchesToClone == null
				|| branchesToClone.isEmpty();

		config.setFetchRefSpecs(calculateRefSpecs(fetchAll
		config.update(clonedRepo.getConfig());

		clonedRepo.getConfig().save();

		FetchCommand command = new FetchCommand(clonedRepo);
		command.setRemote(remote);
		command.setProgressMonitor(monitor);
		command.setTagOpt(fetchAll ? TagOpt.FETCH_TAGS : TagOpt.AUTO_FOLLOW);
		configure(command);

		return command.call();
	}

	private List<RefSpec> calculateRefSpecs(boolean fetchAll
		RefSpec heads = new RefSpec();
		heads = heads.setForceUpdate(true);
		heads = heads.setSourceDestination(Constants.R_HEADS + '*'
		List<RefSpec> specs = new ArrayList<>();
		if (!fetchAll) {
			RefSpec tags = new RefSpec();
			tags = tags.setForceUpdate(true);
			tags = tags.setSourceDestination(Constants.R_TAGS + '*'
					Constants.R_TAGS + '*');
			for (String selectedRef : branchesToClone) {
				if (heads.matchSource(selectedRef)) {
					specs.add(heads.expandFromSource(selectedRef));
				} else if (tags.matchSource(selectedRef)) {
					specs.add(tags.expandFromSource(selectedRef));
				}
			}
		} else {
			specs.add(heads);
		}
		return specs;
	}

	private void checkout(Repository clonedRepo
			throws MissingObjectException
			IOException

		Ref head = null;
		if (branch.equals(Constants.HEAD)) {
			Ref foundBranch = findBranchToCheckout(result);
			if (foundBranch != null)
				head = foundBranch;
		}
		if (head == null) {
			head = result.getAdvertisedRef(branch);
			if (head == null)
				head = result.getAdvertisedRef(Constants.R_HEADS + branch);
			if (head == null)
				head = result.getAdvertisedRef(Constants.R_TAGS + branch);
		}

		if (head == null || head.getObjectId() == null)

		if (head.getName().startsWith(Constants.R_HEADS)) {
			final RefUpdate newHead = clonedRepo.updateRef(Constants.HEAD);
			newHead.disableRefLog();
			newHead.link(head.getName());
			addMergeConfig(clonedRepo
		}

		final RevCommit commit = parseCommit(clonedRepo

		boolean detached = !head.getName().startsWith(Constants.R_HEADS);
		RefUpdate u = clonedRepo.updateRef(Constants.HEAD
		u.setNewObjectId(commit.getId());
		u.forceUpdate();

		if (!bare) {
			DirCache dc = clonedRepo.lockDirCache();
			DirCacheCheckout co = new DirCacheCheckout(clonedRepo
					commit.getTree());
			co.setProgressMonitor(monitor);
			co.checkout();
			if (cloneSubmodules)
				cloneSubmodules(clonedRepo);
		}
	}

	private void cloneSubmodules(Repository clonedRepo) throws IOException
			GitAPIException {
		SubmoduleInitCommand init = new SubmoduleInitCommand(clonedRepo);
		Collection<String> submodules = init.call();
		if (submodules.isEmpty()) {
			return;
		}
		if (callback != null) {
			callback.initializedSubmodules(submodules);
		}

		SubmoduleUpdateCommand update = new SubmoduleUpdateCommand(clonedRepo);
		configure(update);
		update.setProgressMonitor(monitor);
		update.setCallback(callback);
		if (!update.call().isEmpty()) {
			SubmoduleWalk walk = SubmoduleWalk.forIndex(clonedRepo);
			while (walk.next()) {
				try (Repository subRepo = walk.getRepository()) {
					if (subRepo != null) {
						cloneSubmodules(subRepo);
					}
				}
			}
		}
	}

	private Ref findBranchToCheckout(FetchResult result) {
		final Ref idHEAD = result.getAdvertisedRef(Constants.HEAD);
		ObjectId headId = idHEAD != null ? idHEAD.getObjectId() : null;
		if (headId == null) {
			return null;
		}

		Ref master = result.getAdvertisedRef(Constants.R_HEADS
				+ Constants.MASTER);
		ObjectId objectId = master != null ? master.getObjectId() : null;
		if (headId.equals(objectId)) {
			return master;
		}

		Ref foundBranch = null;
		for (Ref r : result.getAdvertisedRefs()) {
			final String n = r.getName();
			if (!n.startsWith(Constants.R_HEADS))
				continue;
			if (headId.equals(r.getObjectId())) {
				foundBranch = r;
				break;
			}
		}
		return foundBranch;
	}

	private void addMergeConfig(Repository clonedRepo
			throws IOException {
		String branchName = Repository.shortenRefName(head.getName());
		clonedRepo.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION
				branchName
		clonedRepo.getConfig().setString(ConfigConstants.CONFIG_BRANCH_SECTION
				branchName
		String autosetupRebase = clonedRepo.getConfig().getString(
				ConfigConstants.CONFIG_BRANCH_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSETUPREBASE);
		if (ConfigConstants.CONFIG_KEY_ALWAYS.equals(autosetupRebase)
				|| ConfigConstants.CONFIG_KEY_REMOTE.equals(autosetupRebase))
			clonedRepo.getConfig().setEnum(
					ConfigConstants.CONFIG_BRANCH_SECTION
					ConfigConstants.CONFIG_KEY_REBASE
		clonedRepo.getConfig().save();
	}

	private RevCommit parseCommit(Repository clonedRepo
			throws MissingObjectException
			IOException {
		final RevCommit commit;
		try (RevWalk rw = new RevWalk(clonedRepo)) {
			commit = rw.parseCommit(ref.getObjectId());
		}
		return commit;
	}

	public CloneCommand setURI(String uri) {
		this.uri = uri;
		return this;
	}

	public CloneCommand setDirectory(File directory) {
		validateDirs(directory
		this.directory = directory;
		return this;
	}

	public CloneCommand setGitDir(File gitDir) {
		validateDirs(directory
		this.gitDir = gitDir;
		return this;
	}

	public CloneCommand setBare(boolean bare) throws IllegalStateException {
		validateDirs(directory
		this.bare = bare;
		return this;
	}

	public CloneCommand setFs(FS fs) {
		this.fs = fs;
		return this;
	}

	public CloneCommand setRemote(String remote) {
		if (remote == null) {
			remote = Constants.DEFAULT_REMOTE_NAME;
		}
		this.remote = remote;
		return this;
	}

	public CloneCommand setBranch(String branch) {
		if (branch == null) {
			branch = Constants.HEAD;
		}
		this.branch = branch;
		return this;
	}

	public CloneCommand setProgressMonitor(ProgressMonitor monitor) {
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}

	public CloneCommand setCloneAllBranches(boolean cloneAllBranches) {
		this.cloneAllBranches = cloneAllBranches;
		return this;
	}

	public CloneCommand setCloneSubmodules(boolean cloneSubmodules) {
		this.cloneSubmodules = cloneSubmodules;
		return this;
	}

	public CloneCommand setBranchesToClone(Collection<String> branchesToClone) {
		this.branchesToClone = branchesToClone;
		return this;
	}

	public CloneCommand setNoCheckout(boolean noCheckout) {
		this.noCheckout = noCheckout;
		return this;
	}

	public CloneCommand setCallback(Callback callback) {
		this.callback = callback;
		return this;
	}

	private static void validateDirs(File directory
			throws IllegalStateException {
		if (directory != null) {
			if (directory.exists() && !directory.isDirectory()) {
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().initFailedDirIsNoDirectory
			}
			if (gitDir != null && gitDir.exists() && !gitDir.isDirectory()) {
				throw new IllegalStateException(MessageFormat.format(
						JGitText.get().initFailedGitDirIsNoDirectory
						gitDir));
			}
			if (bare) {
				if (gitDir != null && !gitDir.equals(directory))
					throw new IllegalStateException(MessageFormat.format(
							JGitText.get().initFailedBareRepoDifferentDirs
							gitDir
			} else {
				if (gitDir != null && gitDir.equals(directory))
					throw new IllegalStateException(MessageFormat.format(
							JGitText.get().initFailedNonBareRepoSameDirs
							gitDir
			}
		}
	}

	private void cleanup() {
		try {
			if (directory != null) {
				if (!directoryExistsInitially) {
					FileUtils.delete(directory
							| FileUtils.SKIP_MISSING | FileUtils.IGNORE_ERRORS);
				} else {
					deleteChildren(directory);
				}
			}
			if (gitDir != null) {
				if (!gitDirExistsInitially) {
					FileUtils.delete(gitDir
							| FileUtils.SKIP_MISSING | FileUtils.IGNORE_ERRORS);
				} else {
					deleteChildren(directory);
				}
			}
		} catch (IOException e) {
		}
	}

	private void deleteChildren(File file) throws IOException {
		File[] files = file.listFiles();
		if (files == null) {
			return;
		}
		for (File child : files) {
			FileUtils.delete(child
					| FileUtils.IGNORE_ERRORS);
		}
	}
}
