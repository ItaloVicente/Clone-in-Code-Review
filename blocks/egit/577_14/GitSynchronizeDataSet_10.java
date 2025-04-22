package org.eclipse.egit.core.synchronize.dto;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jgit.lib.Commit;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Tag;
import org.eclipse.jgit.lib.Tree;

public class GitSynchronizeData {

	private final boolean includeLocal;

	private final Repository repo;

	private final String srcRev;

	private final String dstRev;

	private final Set<IProject> projects;

	private final String repoParentPath;

	public GitSynchronizeData(Repository repository, String srcRef,
			String dstRef, IProject project, boolean includeLocal) {
		this(repository, srcRef, dstRef, new HashSet<IProject>(Arrays
				.asList(project)), includeLocal);
	}

	public GitSynchronizeData(Repository repository, String srcRev,
			String dstRev, Set<IProject> projects, boolean includeLocal) {
		repo = repository;
		this.srcRev = srcRev;
		this.dstRev = dstRev;
		this.projects = projects;
		this.includeLocal = includeLocal;
		repoParentPath = repo.getDirectory().getParentFile().getAbsolutePath();
	}

	public Repository getRepository() {
		return repo;
	}

	public String getSrcRev() {
		return srcRev;
	}

	public String getDstRev() {
		return dstRev;
	}

	public Tree mapSrcTree() throws IOException {
		return mapTree(srcRev);
	}

	public Tree mapDstTree() throws IOException {
		return mapTree(dstRev);
	}

	public ObjectId getSrcObjectId() throws IOException {
		return getObjecId(srcRev);
	}

	public ObjectId getDstObjectId() throws IOException {
		return getObjecId(dstRev);
	}

	public Set<IProject> getProjects() {
		return Collections.unmodifiableSet(projects);
	}

	public boolean contains(IResource resource) {
		return resource.getFullPath().toString().startsWith(repoParentPath);
	}

	public boolean contains(File file) {
		return file.getAbsoluteFile().toString().startsWith(repoParentPath);
	}

	public boolean shouldIncludeLocal() {
		return includeLocal;
	}

	private Tree mapTree(String rev) throws IOException {
		if (rev.startsWith(Constants.R_TAGS)) {
			Tag tag = repo.mapTag(rev);
			Commit commit = repo.mapCommit(tag.getObjId());
			return commit.getTree();
		} else {
			return repo.mapTree(rev);
		}
	}

	private ObjectId getObjecId(String rev) throws IOException {
		if (rev.startsWith(Constants.R_TAGS)) {
			return repo.mapTag(rev).getObjId();
		} else {
			return repo.mapCommit(rev).getCommitId();
		}
	}

}
