package org.eclipse.jgit.api;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.lib.SubmoduleConfig.FetchRecurseSubmodulesMode;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.TagOpt;
import org.eclipse.jgit.transport.Transport;

public class FetchCommand extends TransportCommand<FetchCommand
	private String remote = Constants.DEFAULT_REMOTE_NAME;

	private List<RefSpec> refSpecs;

	private ProgressMonitor monitor = NullProgressMonitor.INSTANCE;

	private boolean checkFetchedObjects;

	private Boolean removeDeletedRefs;

	private boolean dryRun;

	private boolean thin = Transport.DEFAULT_FETCH_THIN;

	private TagOpt tagOption;

	private FetchRecurseSubmodulesMode submoduleRecurseMode = null;

	private Callback callback;

	private boolean isForceUpdate;

	public interface Callback {
		void fetchingSubmodule(String name);
	}

	protected FetchCommand(Repository repo) {
		super(repo);
		refSpecs = new ArrayList<>(3);
	}

	private FetchRecurseSubmodulesMode getRecurseMode(String path) {
		if (submoduleRecurseMode != null) {
			return submoduleRecurseMode;
		}

		FetchRecurseSubmodulesMode mode = repo.getConfig().getEnum(
				FetchRecurseSubmodulesMode.values()
				ConfigConstants.CONFIG_SUBMODULE_SECTION
				ConfigConstants.CONFIG_KEY_FETCH_RECURSE_SUBMODULES
		if (mode != null) {
			return mode;
		}

		mode = repo.getConfig().getEnum(FetchRecurseSubmodulesMode.values()
				ConfigConstants.CONFIG_FETCH_SECTION
				ConfigConstants.CONFIG_KEY_RECURSE_SUBMODULES
		if (mode != null) {
			return mode;
		}

		return FetchRecurseSubmodulesMode.ON_DEMAND;
	}

	private void fetchSubmodules(FetchResult results)
			throws org.eclipse.jgit.api.errors.TransportException
			GitAPIException
		try (SubmoduleWalk walk = new SubmoduleWalk(repo);
				RevWalk revWalk = new RevWalk(repo)) {
			ObjectId fetchHead = repo.resolve(Constants.FETCH_HEAD);
			if (fetchHead == null) {
				return;
			}
			walk.setTree(revWalk.parseTree(fetchHead));
			while (walk.next()) {
				try (Repository submoduleRepo = walk.getRepository()) {

					if (submoduleRepo == null || walk.getModulesPath() == null
							|| walk.getConfigUrl() == null) {
						continue;
					}

					FetchRecurseSubmodulesMode recurseMode = getRecurseMode(
							walk.getPath());

					if ((recurseMode == FetchRecurseSubmodulesMode.ON_DEMAND
							&& !submoduleRepo.getObjectDatabase()
									.has(walk.getObjectId()))
							|| recurseMode == FetchRecurseSubmodulesMode.YES) {
						FetchCommand f = new FetchCommand(submoduleRepo)
								.setProgressMonitor(monitor)
								.setTagOpt(tagOption)
								.setCheckFetchedObjects(checkFetchedObjects)
								.setRemoveDeletedRefs(isRemoveDeletedRefs())
								.setThin(thin)
								.setRefSpecs(applyOptions(refSpecs))
								.setDryRun(dryRun)
								.setRecurseSubmodules(recurseMode);
						configure(f);
						if (callback != null) {
							callback.fetchingSubmodule(walk.getPath());
						}
						results.addSubmodule(walk.getPath()
					}
				}
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		} catch (ConfigInvalidException e) {
			throw new InvalidConfigurationException(e.getMessage()
		}
	}

	@Override
	public FetchResult call() throws GitAPIException
			org.eclipse.jgit.api.errors.TransportException {
		checkCallable();

		try (Transport transport = Transport.open(repo
			transport.setCheckFetchedObjects(checkFetchedObjects);
			transport.setRemoveDeletedRefs(isRemoveDeletedRefs());
			transport.setDryRun(dryRun);
			if (tagOption != null)
				transport.setTagOpt(tagOption);
			transport.setFetchThin(thin);
			configure(transport);
			FetchResult result = transport.fetch(monitor
					applyOptions(refSpecs));
			if (!repo.isBare()) {
				fetchSubmodules(result);
			}

			return result;
		} catch (NoRemoteRepositoryException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote
		} catch (TransportException e) {
			throw new org.eclipse.jgit.api.errors.TransportException(
					e.getMessage()
		} catch (URISyntaxException e) {
			throw new InvalidRemoteException(MessageFormat.format(
					JGitText.get().invalidRemote
		} catch (NotSupportedException e) {
			throw new JGitInternalException(
					JGitText.get().exceptionCaughtDuringExecutionOfFetchCommand
					e);
		}

	}

	private List<RefSpec> applyOptions(List<RefSpec> refSpecs2) {
		if (!isForceUpdate()) {
			return refSpecs2;
		}
		List<RefSpec> updated = new ArrayList<>(3);
		for (RefSpec refSpec : refSpecs2) {
			updated.add(refSpec.setForceUpdate(true));
		}
		return updated;
	}

	public FetchCommand setRecurseSubmodules(
			@Nullable FetchRecurseSubmodulesMode recurse) {
		checkCallable();
		submoduleRecurseMode = recurse;
		return this;
	}

	public FetchCommand setRemote(String remote) {
		checkCallable();
		this.remote = remote;
		return this;
	}

	public String getRemote() {
		return remote;
	}

	public int getTimeout() {
		return timeout;
	}

	public boolean isCheckFetchedObjects() {
		return checkFetchedObjects;
	}

	public FetchCommand setCheckFetchedObjects(boolean checkFetchedObjects) {
		checkCallable();
		this.checkFetchedObjects = checkFetchedObjects;
		return this;
	}

	public boolean isRemoveDeletedRefs() {
		if (removeDeletedRefs != null)
			return removeDeletedRefs.booleanValue();
			boolean result = false;
			StoredConfig config = repo.getConfig();
			result = config.getBoolean(ConfigConstants.CONFIG_FETCH_SECTION
					null
			result = config.getBoolean(ConfigConstants.CONFIG_REMOTE_SECTION
					remote
			return result;
		}
	}

	public FetchCommand setRemoveDeletedRefs(boolean removeDeletedRefs) {
		checkCallable();
		this.removeDeletedRefs = Boolean.valueOf(removeDeletedRefs);
		return this;
	}

	public ProgressMonitor getProgressMonitor() {
		return monitor;
	}

	public FetchCommand setProgressMonitor(ProgressMonitor monitor) {
		checkCallable();
		if (monitor == null) {
			monitor = NullProgressMonitor.INSTANCE;
		}
		this.monitor = monitor;
		return this;
	}

	public List<RefSpec> getRefSpecs() {
		return refSpecs;
	}

	public FetchCommand setRefSpecs(String... specs) {
		return setRefSpecs(
				Arrays.stream(specs).map(RefSpec::new).collect(toList()));
	}

	public FetchCommand setRefSpecs(RefSpec... specs) {
		return setRefSpecs(Arrays.asList(specs));
	}

	public FetchCommand setRefSpecs(List<RefSpec> specs) {
		checkCallable();
		this.refSpecs.clear();
		this.refSpecs.addAll(specs);
		return this;
	}

	public boolean isDryRun() {
		return dryRun;
	}

	public FetchCommand setDryRun(boolean dryRun) {
		checkCallable();
		this.dryRun = dryRun;
		return this;
	}

	public boolean isThin() {
		return thin;
	}

	public FetchCommand setThin(boolean thin) {
		checkCallable();
		this.thin = thin;
		return this;
	}

	public FetchCommand setTagOpt(TagOpt tagOpt) {
		checkCallable();
		this.tagOption = tagOpt;
		return this;
	}

	public FetchCommand setCallback(Callback callback) {
		this.callback = callback;
		return this;
	}

	public boolean isForceUpdate() {
		return this.isForceUpdate;
	}

	public FetchCommand setForceUpdate(boolean force) {
		this.isForceUpdate = force;
		return this;
	}
}
