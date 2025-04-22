package org.eclipse.egit.ui.internal.synchronize.model;

import java.util.Map;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.egit.core.synchronize.GitCommitsModelCache.Change;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.synchronize.model.TreeBuilder.FileModelFactory;
import org.eclipse.egit.ui.internal.synchronize.model.TreeBuilder.TreeModelFactory;
import org.eclipse.jgit.lib.Repository;

public class GitModelCache extends GitModelObjectContainer {

	private final Path location;

	private final Repository repo;

	private GitModelObject[] children;

	public GitModelCache(GitModelRepository parent, Repository repo,
			Map<String, Change> cache) {
		this(parent, repo, cache, new FileModelFactory() {
			@Override
			public GitModelBlob createFileModel(
					GitModelObjectContainer objParent, Repository nestedRepo,
					Change change, IPath path) {
				return new GitModelCacheFile(objParent, nestedRepo, change,
						path);
			}

			@Override
			public boolean isWorkingTree() {
				return false;
			}
		});
	}

	protected GitModelCache(GitModelRepository parent, final Repository repo,
			Map<String, Change> changes, final FileModelFactory fileFactory) {
		super(parent);
		this.repo = repo;
		this.location = new Path(repo.getWorkTree().toString());

		this.children = TreeBuilder.build(this, repo, changes, fileFactory,
				new TreeModelFactory() {
					@Override
					public GitModelTree createTreeModel(
							GitModelObjectContainer parentObject,
							IPath fullPath,
							int kind) {
						return new GitModelCacheTree(parentObject, repo,
								fullPath, fileFactory);
					}
				});
	}

	@Override
	public String getName() {
		return UIText.GitModelIndex_index;
	}

	@Override
	public GitModelObject[] getChildren() {
		return children;
	}

	@Override
	public int getKind() {
		return Differencer.CHANGE | Differencer.RIGHT;
	}

	@Override
	public int repositoryHashCode() {
		return repo.getWorkTree().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		if (obj instanceof GitModelCache
				&& !(obj instanceof GitModelWorkingTree)) {
			GitModelCache left = (GitModelCache) obj;
			return left.getParent().equals(getParent());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return repositoryHashCode();
	}

	@Override
	public IPath getLocation() {
		return location;
	}

	@Override
	public String toString() {
		return "ModelCache"; //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		if (children != null) {
			for (GitModelObject object : children)
				object.dispose();
			children = null;
		}
	}
}
