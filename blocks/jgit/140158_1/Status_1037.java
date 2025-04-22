package org.eclipse.jgit.api;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRefNameException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class StashListCommand extends GitCommand<Collection<RevCommit>> {

	public StashListCommand(Repository repo) {
		super(repo);
	}

	@Override
	public Collection<RevCommit> call() throws GitAPIException
			InvalidRefNameException {
		checkCallable();

		try {
			if (repo.exactRef(Constants.R_STASH) == null)
				return Collections.emptyList();
		} catch (IOException e) {
			throw new InvalidRefNameException(MessageFormat.format(
					JGitText.get().cannotRead
		}

		final ReflogCommand refLog = new ReflogCommand(repo);
		refLog.setRef(Constants.R_STASH);
		final Collection<ReflogEntry> stashEntries = refLog.call();
		if (stashEntries.isEmpty())
			return Collections.emptyList();

		final List<RevCommit> stashCommits = new ArrayList<>(
				stashEntries.size());
		try (RevWalk walk = new RevWalk(repo)) {
			for (ReflogEntry entry : stashEntries) {
				try {
					stashCommits.add(walk.parseCommit(entry.getNewId()));
				} catch (IOException e) {
					throw new JGitInternalException(MessageFormat.format(
							JGitText.get().cannotReadCommit
							e);
				}
			}
		}
		return stashCommits;
	}
}
