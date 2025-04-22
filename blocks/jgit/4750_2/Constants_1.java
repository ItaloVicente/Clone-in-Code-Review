package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.ReflogEntry;

public class StashListCommand extends GitCommand<Collection<RevCommit>> {

	public StashListCommand(final Repository repo) {
		super(repo);
	}

	public Collection<RevCommit> call() throws Exception {
		checkCallable();

		final ReflogCommand refLog = new ReflogCommand(repo);
		refLog.setRef(Constants.R_STASH);
		final Collection<ReflogEntry> stashEntries = refLog.call();
		if (stashEntries.isEmpty())
			return Collections.emptyList();

		final List<RevCommit> stashCommits = new ArrayList<RevCommit>(
				stashEntries.size());
		final RevWalk walk = new RevWalk(repo);
		walk.setRetainBody(true);
		try {
			for (ReflogEntry entry : stashEntries)
				try {
					stashCommits.add(walk.parseCommit(entry.getNewId()));
				} catch (IOException e) {
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().cannotReadCommit
							e);
				}
		} finally {
			walk.dispose();
		}
		return stashCommits;
	}
}
