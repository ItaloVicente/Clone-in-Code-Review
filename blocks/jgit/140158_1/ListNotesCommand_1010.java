package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.HEAD;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.RevWalkUtils;

public class ListBranchCommand extends GitCommand<List<Ref>> {
	private ListMode listMode;

	private String containsCommitish;

	public enum ListMode {
		ALL
		REMOTE;
	}

	protected ListBranchCommand(Repository repo) {
		super(repo);
	}

	@Override
	public List<Ref> call() throws GitAPIException {
		checkCallable();
		List<Ref> resultRefs;
		try {
			Collection<Ref> refs = new ArrayList<>();

			Ref head = repo.exactRef(HEAD);
			if (head != null && head.getLeaf().getName().equals(HEAD)) {
				refs.add(head);
			}

			if (listMode == null) {
				refs.addAll(repo.getRefDatabase().getRefsByPrefix(R_HEADS));
			} else if (listMode == ListMode.REMOTE) {
				refs.addAll(repo.getRefDatabase().getRefsByPrefix(R_REMOTES));
			} else {
				refs.addAll(repo.getRefDatabase().getRefsByPrefix(R_HEADS
						R_REMOTES));
			}
			resultRefs = new ArrayList<>(filterRefs(refs));
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}

		Collections.sort(resultRefs
			@Override
			public int compare(Ref o1
				return o1.getName().compareTo(o2.getName());
			}
		});
		setCallable(false);
		return resultRefs;
	}

	private Collection<Ref> filterRefs(Collection<Ref> refs)
			throws RefNotFoundException
		if (containsCommitish == null)
			return refs;

		try (RevWalk walk = new RevWalk(repo)) {
			ObjectId resolved = repo.resolve(containsCommitish);
			if (resolved == null)
				throw new RefNotFoundException(MessageFormat.format(
						JGitText.get().refNotResolved

			RevCommit containsCommit = walk.parseCommit(resolved);
			return RevWalkUtils.findBranchesReachableFrom(containsCommit
					refs);
		}
	}

	public ListBranchCommand setListMode(ListMode listMode) {
		checkCallable();
		this.listMode = listMode;
		return this;
	}

	public ListBranchCommand setContains(String containsCommitish) {
		checkCallable();
		this.containsCommitish = containsCommitish;
		return this;
	}
}
