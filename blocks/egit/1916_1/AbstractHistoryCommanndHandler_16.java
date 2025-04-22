package org.eclipse.egit.ui.internal.history;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.jgit.lib.Repository;

public class HistoryPageInput {
	private final IResource[] list;

	private final File[] files;

	private final Repository repo;

	private final Object singleFile;

	public HistoryPageInput(final Repository repository,
			final IResource[] resourceItems) {
		this.repo = repository;
		list = resourceItems;
		if (resourceItems.length == 1
				&& resourceItems[0].getType() == IResource.FILE)
			singleFile = resourceItems[0];
		else
			singleFile = null;
		files = null;
	}

	public HistoryPageInput(final Repository repository, final File[] fileItems) {
		this.repo = repository;
		list = null;
		if (fileItems.length == 1 && fileItems[0].isFile())
			singleFile = fileItems[0];
		else
			singleFile = null;
		files = fileItems;
	}

	public HistoryPageInput(final Repository repository) {
		this.repo = repository;
		list = null;
		singleFile = null;
		files = null;
	}

	public Repository getRepository() {
		return repo;
	}

	public IResource[] getItems() {
		return list;
	}

	public File[] getFileList() {
		return files;
	}

	public Object getSingleFile() {
		return singleFile;
	}

	public boolean isSingleFile() {
		return singleFile != null;
	}
}
