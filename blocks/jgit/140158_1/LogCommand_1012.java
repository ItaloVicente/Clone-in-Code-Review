package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevWalk;

public class ListTagCommand extends GitCommand<List<Ref>> {

	protected ListTagCommand(Repository repo) {
		super(repo);
	}

	@Override
	public List<Ref> call() throws GitAPIException {
		checkCallable();
		List<Ref> tags = new ArrayList<>();
		try (RevWalk revWalk = new RevWalk(repo)) {
			List<Ref> refList = repo.getRefDatabase()
					.getRefsByPrefix(Constants.R_TAGS);
			for (Ref ref : refList) {
				tags.add(ref);
			}
		} catch (IOException e) {
			throw new JGitInternalException(e.getMessage()
		}
		Collections.sort(tags
			@Override
			public int compare(Ref o1
				return o1.getName().compareTo(o2.getName());
			}
		});
		setCallable(false);
		return tags;
	}

}
