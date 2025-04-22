
package org.eclipse.jgit.pgm;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.api.FetchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.SubmoduleConfig.FetchRecurseSubmodulesMode;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.TagOpt;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

@Command(common = true
class Fetch extends AbstractFetchCommand implements FetchCommand.Callback {
	@Option(name = "--timeout"
	int timeout = -1;

	@Option(name = "--fsck"
	private Boolean fsck;

	@Option(name = "--no-fsck")
	void nofsck(@SuppressWarnings("unused") final boolean ignored) {
		fsck = Boolean.FALSE;
	}

	@Option(name = "--prune"
	private Boolean prune;

	@Option(name = "--dry-run")
	private boolean dryRun;

	@Option(name = "--thin"
	private Boolean thin;

	@Option(name = "--no-thin")
	void nothin(@SuppressWarnings("unused") final boolean ignored) {
		thin = Boolean.FALSE;
	}

	@Option(name = "--quiet"
	private Boolean quiet;

	@Option(name = "--tags"
	private Boolean tags;

	@Option(name = "--no-tags"
	void notags(@SuppressWarnings("unused")
	final boolean ignored) {
		tags = Boolean.FALSE;
	}

	@Option(name = "--force"
	private Boolean force;

	private FetchRecurseSubmodulesMode recurseSubmodules;

	@Option(name = "--recurse-submodules"
	void recurseSubmodules(String mode) {
		if (mode == null || mode.isEmpty()) {
			recurseSubmodules = FetchRecurseSubmodulesMode.YES;
		} else {
			for (FetchRecurseSubmodulesMode m : FetchRecurseSubmodulesMode
					.values()) {
				if (m.matchConfigValue(mode)) {
					recurseSubmodules = m;
					return;
				}
			}
			throw die(MessageFormat
					.format(CLIText.get().invalidRecurseSubmodulesMode
		}
	}

	@Option(name = "--no-recurse-submodules"
	void noRecurseSubmodules(@SuppressWarnings("unused")
	final boolean ignored) {
		recurseSubmodules = FetchRecurseSubmodulesMode.NO;
	}

	@Argument(index = 0
	private String remote = Constants.DEFAULT_REMOTE_NAME;

	@Argument(index = 1
	private List<RefSpec> toget;

	@Override
	protected void run() {
		try (Git git = new Git(db)) {
			FetchCommand fetch = git.fetch();
			if (fsck != null) {
				fetch.setCheckFetchedObjects(fsck.booleanValue());
			}
			if (prune != null) {
				fetch.setRemoveDeletedRefs(prune.booleanValue());
			}
			if (toget != null) {
				fetch.setRefSpecs(toget);
			}
			if (tags != null) {
				fetch.setTagOpt(tags.booleanValue() ? TagOpt.FETCH_TAGS
						: TagOpt.NO_TAGS);
			}
			if (0 <= timeout) {
				fetch.setTimeout(timeout);
			}
			fetch.setDryRun(dryRun);
			fetch.setRemote(remote);
			if (thin != null) {
				fetch.setThin(thin.booleanValue());
			}
			if (quiet == null || !quiet.booleanValue()) {
				fetch.setProgressMonitor(new TextProgressMonitor(errw));
			}
			fetch.setRecurseSubmodules(recurseSubmodules).setCallback(this);
			if (force != null) {
				fetch.setForceUpdate(force.booleanValue());
			}

			FetchResult result = fetch.call();
			if (result.getTrackingRefUpdates().isEmpty()
					&& result.submoduleResults().isEmpty()) {
				return;
			}
			showFetchResult(result);
		} catch (GitAPIException | IOException e) {
			throw die(e.getMessage()
		}
	}

	@Override
	public void fetchingSubmodule(String name) {
		try {
			outw.println(MessageFormat.format(CLIText.get().fetchingSubmodule
					name));
			outw.flush();
		} catch (IOException e) {
		}
	}
}
