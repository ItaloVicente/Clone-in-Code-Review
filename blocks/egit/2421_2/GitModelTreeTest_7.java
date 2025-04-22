package org.eclipse.egit.ui.internal.synchronize.model;

import static org.eclipse.compare.structuremergeviewer.Differencer.LEFT;
import static org.eclipse.jgit.lib.Constants.HEAD;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.synchronize.dto.GitSynchronizeData;
import org.eclipse.egit.ui.common.LocalRepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepository;

abstract class GitModelTestCase extends LocalRepositoryTestCase {

	protected static File leftRepoFile;

	protected static File rightRepoFile;

	protected GitModelRepository createModelRepository() throws Exception {
		return new GitModelRepository(getGSD(lookupRepository(leftRepoFile)));
	}

	protected GitModelCommit createModelCommit() throws Exception {
		return new GitModelCommit(createModelRepository(), getCommit(
				leftRepoFile,
				HEAD), LEFT);
	}

	protected RevCommit getCommit(File repoDir, String revStr)
			throws Exception {
		FileRepository repo = lookupRepository(repoDir);
		return new RevWalk(repo).parseCommit(repo
				.resolve(revStr));
	}

	protected GitSynchronizeData getGSD(Repository repo) throws IOException {
		return new GitSynchronizeData(repo, Constants.HEAD,
				Constants.HEAD, true);
	}

	protected IPath getTreeLocation() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder")).getLocation();
	}

	protected IPath getTree1Location() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder1")).getLocation();
	}

	protected IPath getFile1Location() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder/test.txt")).getLocation();
	}

	protected IPath getFile2Location() {
		return ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFile(new Path("folder/test1.txt")).getLocation();
	}

}
