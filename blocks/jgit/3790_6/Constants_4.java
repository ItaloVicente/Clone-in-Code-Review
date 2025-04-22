package org.eclipse.jgit.api;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.lib.Repository;

public class StashListCommand extends GitCommand<Set<String>> {
	protected StashListCommand(Repository repo) {
		super(repo);
	}

	public Set<String> call() throws Exception {
		System.out.println(repo.resolve("refs/stash"));
		return null;
	}

	public String getStashId() throws AmbiguousObjectException
		String recentStash = repo.resolve("refs/stash").name();
		System.out.println(recentStash);
		return null;
	}
}
