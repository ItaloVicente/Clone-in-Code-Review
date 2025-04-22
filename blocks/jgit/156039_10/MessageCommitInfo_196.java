package org.eclipse.jgit.niofs.internal.op.model;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.revwalk.RevCommit;

public class MergeCommitContent extends DefaultCommitContent {

	private final List<RevCommit> parents;

	public MergeCommitContent(final Map<String
		super(content);

		this.parents = parents;
	}

	public List<RevCommit> getParents() {
		return parents;
	}
}
