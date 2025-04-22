package org.eclipse.jgit.api;

import java.util.Set;
import org.eclipse.jgit.lib.Repository;

public class StashApplyCommand extends GitCommand<Set<String>> {

	protected StashApplyCommand(Repository repo) {
		super(repo);
	}

	public Set<String> call() throws Exception {


		return null;
	}
}
