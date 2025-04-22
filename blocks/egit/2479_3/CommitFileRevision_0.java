package org.eclipse.egit.core;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

public class GitFileRevisionReference {

	private Repository repository;
	private RevCommit revCommit;
	private String path;

	public GitFileRevisionReference(Repository r, RevCommit revCommit, String path) {
		this.repository = r;
		this.revCommit = revCommit;
		this.path = path;
	}

	public Repository getRepository() {
		return repository;
	}

	public RevCommit getRevCommit() {
		return revCommit;
	}

	public String getPath() {
		return path;
	}
}
