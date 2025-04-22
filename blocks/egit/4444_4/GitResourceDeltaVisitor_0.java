package org.eclipse.egit.core.internal.indexdiff;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.core.project.RepositoryMapping;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.team.core.Team;

public class GitResourceDeltaVisitor implements IResourceDeltaVisitor {

	private static final String GITIGNORE_NAME = ".gitignore"; //$NON-NLS-1$

	private static int INTERESTING_CHANGES = IResourceDelta.CONTENT
			| IResourceDelta.MOVED_FROM | IResourceDelta.MOVED_TO
			| IResourceDelta.OPEN | IResourceDelta.REPLACED
			| IResourceDelta.TYPE;

	private final Repository repository;

	private final Collection<String> filesToUpdate;

	private final Collection<IFile> fileResourcesToUpdate;

	private boolean gitIgnoreChanged = false;

	public GitResourceDeltaVisitor(Repository repository) {
		this.repository = repository;

		filesToUpdate = new HashSet<String>();
		fileResourcesToUpdate = new HashSet<IFile>();
	}

	public boolean visit(IResourceDelta delta) throws CoreException {
		final IResource resource = delta.getResource();
		if (Team.isIgnoredHint(resource))
			return false;

		if (delta.getKind() == IResourceDelta.CHANGED
				&& (delta.getFlags() & INTERESTING_CHANGES) == 0)
			return true;

		if (resource.getType() != IResource.FILE)
			return true;

		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(resource);
		if (mapping == null || mapping.getRepository() != repository)
			return true;

		if (resource.getName().equals(GITIGNORE_NAME)) {
			gitIgnoreChanged = true;
			return false;
		}

		String repoRelativePath = mapping.getRepoRelativePath(resource);
		filesToUpdate.add(repoRelativePath);
		fileResourcesToUpdate.add((IFile) resource);

		return true;
	}

	public Collection<IFile> getFileResourcesToUpdate() {
		return fileResourcesToUpdate;
	}

	public Collection<String> getFilesToUpdate() {
		return filesToUpdate;
	}

	public boolean getGitIgnoreChanged() {
		return gitIgnoreChanged;
	}
}
