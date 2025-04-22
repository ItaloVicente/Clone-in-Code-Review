package org.eclipse.jgit.api;

import static org.eclipse.jgit.util.FileUtils.RECURSIVE;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.submodule.SubmoduleWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;

public class SubmoduleDeinitCommand
		extends GitCommand<Collection<SubmoduleDeinitResult>> {

	private final Collection<String> paths;

	private boolean force;

	public SubmoduleDeinitCommand(Repository repo) {
		super(repo);
		paths = new ArrayList<>();
	}

	@Override
	public Collection<SubmoduleDeinitResult> call() throws GitAPIException {
		checkCallable();
		try {
			if (paths.isEmpty()) {
				return Collections.emptyList();
			}
			for (String path : paths) {
				if (!submoduleExists(path)) {
					throw new NoSuchSubmoduleException(path);
				}
			}
			List<SubmoduleDeinitResult> results = new ArrayList<>(paths.size());
			try (RevWalk revWalk = new RevWalk(repo);
					SubmoduleWalk generator = SubmoduleWalk.forIndex(repo)) {
				generator.setFilter(PathFilterGroup.createFromStrings(paths));
				StoredConfig config = repo.getConfig();
				while (generator.next()) {
					String path = generator.getPath();
					String name = generator.getModuleName();
					SubmoduleDeinitStatus status = checkDirty(revWalk
					switch (status) {
					case SUCCESS:
						deinit(path);
						break;
					case ALREADY_DEINITIALIZED:
						break;
					case DIRTY:
						if (force) {
							deinit(path);
							status = SubmoduleDeinitStatus.FORCED;
						}
						break;
					default:
						throw new JGitInternalException(MessageFormat.format(
								JGitText.get().unexpectedSubmoduleStatus
								status));
					}

					config.unsetSection(
							ConfigConstants.CONFIG_SUBMODULE_SECTION
					results.add(new SubmoduleDeinitResult(path
				}
			}
			return results;
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
	}

	private void deinit(String path) throws IOException {
		File dir = new File(repo.getWorkTree()
		if (!dir.isDirectory()) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().expectedDirectoryNotSubmodule
		}
		final File[] ls = dir.listFiles();
		if (ls != null) {
                    for (File l : ls) {
                        FileUtils.delete(l
                    }
		}
	}

	private SubmoduleDeinitStatus checkDirty(RevWalk revWalk
			throws GitAPIException
		if (head == null) {
			throw new NoHeadException(
					JGitText.get().invalidRepositoryStateNoHead);
		}
		RevCommit headCommit = revWalk.parseCommit(head.getObjectId());
		RevTree tree = headCommit.getTree();

		ObjectId submoduleHead;
		try (SubmoduleWalk w = SubmoduleWalk.forPath(repo
			submoduleHead = w.getHead();
			if (submoduleHead == null) {
				return SubmoduleDeinitStatus.ALREADY_DEINITIALIZED;
			}
			if (!submoduleHead.equals(w.getObjectId())) {
				return SubmoduleDeinitStatus.DIRTY;
			}
		}

		try (SubmoduleWalk w = SubmoduleWalk.forIndex(repo)) {
			if (!w.next()) {
				return SubmoduleDeinitStatus.DIRTY;
			}
			if (!submoduleHead.equals(w.getObjectId())) {
				return SubmoduleDeinitStatus.DIRTY;
			}

			try (Repository submoduleRepo = w.getRepository()) {
				Status status = Git.wrap(submoduleRepo).status().call();
				return status.isClean() ? SubmoduleDeinitStatus.SUCCESS
						: SubmoduleDeinitStatus.DIRTY;
			}
		}
	}

	private boolean submoduleExists(String path) throws IOException {
		TreeFilter filter = PathFilter.create(path);
		try (SubmoduleWalk w = SubmoduleWalk.forIndex(repo)) {
			return w.setFilter(filter).next();
		}
	}

	public SubmoduleDeinitCommand addPath(String path) {
		paths.add(path);
		return this;
	}

	public SubmoduleDeinitCommand setForce(boolean force) {
		this.force = force;
		return this;
	}

	public static class NoSuchSubmoduleException extends GitAPIException {
		private static final long serialVersionUID = 1L;

		public NoSuchSubmoduleException(String path) {
			super(MessageFormat.format(JGitText.get().noSuchSubmodule
		}
	}

	public enum SubmoduleDeinitStatus {
		ALREADY_DEINITIALIZED
		SUCCESS
		FORCED
		DIRTY
	}
}
