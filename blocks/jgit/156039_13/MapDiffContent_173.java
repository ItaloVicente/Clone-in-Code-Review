package org.eclipse.jgit.niofs.internal.op.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public class ListRefs {

	private final Repository repo;

	public ListRefs(final Repository repo) {
		this.repo = repo;
	}

	public List<Ref> execute() {
		try {
			return new ArrayList<>(repo.getRefDatabase().getRefsByPrefix("refs/heads/"));
		} catch (java.io.IOException e) {
			throw new RuntimeException(e);
		}
	}
}
