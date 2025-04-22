package org.eclipse.egit.ui.internal.history;

import java.io.File;
import java.util.Arrays;

import org.eclipse.core.resources.IResource;
import org.eclipse.jgit.lib.Repository;

public class HistoryPageInput {
	private final IResource[] list;

	private final Repository repo;

	private final File[] fileList;

	public HistoryPageInput(final Repository repository, final IResource[] items) {
		list = items;
		repo = repository;
		fileList = null;
	}

	public HistoryPageInput(final Repository repository) {
		repo = repository;
		list = null;
		fileList = null;
	}

	public HistoryPageInput(final Repository repository, File[] files) {
		repo = repository;
		list = null;
		fileList = files;
	}

	public IResource[] getItems() {
		return list;
	}

	public Repository getRepository() {
		return repo;
	}

	public File[] getFileList() {
		return fileList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(fileList);
		result = prime * result + Arrays.hashCode(list);
		result = prime * result + repo.getWorkTree().hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HistoryPageInput other = (HistoryPageInput) obj;
		if (!Arrays.equals(fileList, other.fileList))
			return false;
		if (!Arrays.equals(list, other.list))
			return false;
		if (!repo.getWorkTree().equals(other.repo.getWorkTree()))
			return false;
		return true;
	}
}
