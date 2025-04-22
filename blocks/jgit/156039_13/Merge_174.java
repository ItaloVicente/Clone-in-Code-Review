package org.eclipse.jgit.niofs.internal.op.commands;

import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotEmpty;
import static org.eclipse.jgit.niofs.internal.util.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.niofs.internal.op.Git;
import org.eclipse.jgit.niofs.internal.op.exceptions.GitException;
import org.eclipse.jgit.revwalk.RevCommit;

public class MapDiffContent {

	private final Git git;
	private final String branch;
	private final String startCommitId;
	private final String endCommitId;

	public MapDiffContent(final Git git
		this.git = checkNotNull("git"
		this.branch = checkNotEmpty("branch"
		this.startCommitId = checkNotEmpty("startCommitId"
		this.endCommitId = checkNotEmpty("endCommitId"
	}

	public Map<String
		BranchUtil.existsBranch(git

		final RevCommit startCommit = git.getCommit(startCommitId);
		final RevCommit endCommit = git.getCommit(endCommitId);

		if (startCommit == null || endCommit == null) {
			throw new GitException("Given commit ids cannot be found.");
		}

		Map<String

		final List<DiffEntry> diffs = git.listDiffs(startCommit.getTree()

		diffs.forEach(entry -> {
			if (entry.getChangeType() != DiffEntry.ChangeType.DELETE) {
				try (final InputStream inputStream = git.blobAsInputStream(branch
					final File file = File.createTempFile("gitz"

					Files.copy(inputStream

					content.put(entry.getNewPath()
				} catch (IOException e) {
					throw new GitException("Unable to get content from diffs"
				}
			} else {
				content.put(entry.getOldPath()
			}
		});

		return content;
	}
}
