package org.eclipse.egit.ui.internal.staging;

import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.jgit.lib.Repository;

abstract class StatusNode {

	private Set<IFile> files;

	private Repository repository;

	StatusNode(Repository repository) {
		this.repository = repository;
	}

	void setFiles(Set<IFile> files) {
		this.files = files;
	}

	Set<IFile> getFiles() {
		return files;
	}

	abstract String getLabel();

	Repository getRepository() {
		return repository;
	}

	public boolean equals(Object other) {
		if (other.getClass() == getClass())
			return repository == ((StatusNode) other).repository;
		return false;
	}

	public int hashCode() {
		return 31 * getClass().getName().hashCode() * repository.hashCode();
	}

}
