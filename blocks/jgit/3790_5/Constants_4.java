package org.eclipse.jgit.api;

import java.util.Set;

import org.eclipse.jgit.lib.Repository;

public class StashListCommand extends GitCommand<Set<String>> {
	protected StashListCommand(Repository repo) {
		super(repo);
	}

	public Set<String> call() throws Exception {
		System.out.println(repo.resolve("refs/stash"));
		return null;
	}
}
