package org.eclipse.egit.ui.internal.repository.tree;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.egit.ui.internal.CommonUtils;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;

public abstract class RepositoryTreeNode<T> extends PlatformObject implements Comparable<RepositoryTreeNode> {

	private Repository myRepository;

	private T myObject;

	private final RepositoryTreeNodeType myType;

	private final RepositoryTreeNode myParent;

	public RepositoryTreeNode(RepositoryTreeNode parent,
			RepositoryTreeNodeType type, Repository repository, T treeObject) {
		myParent = parent;
		myRepository = repository;
		myType = type;
		myObject = treeObject;
	}

	public RepositoryTreeNode getParent() {
		return myParent;
	}

	public RepositoryTreeNodeType getType() {
		return myType;
	}

	public Repository getRepository() {
		return myRepository;
	}

	public IPath getPath() {
		Repository repository = getRepository();
		if (repository == null) {
			return null;
		}
		return new Path(getRepository().getWorkTree().getAbsolutePath());
	}

	public void clear() {
		myRepository = null;
		myObject = null;
	}

	public T getObject() {
		return myObject;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		switch (myType) {
		case REPO:
		case REMOTES:
		case LOCAL:
		case REMOTETRACKING:
		case BRANCHES:
		case ADDITIONALREFS:
		case SUBMODULES:
		case STASH:
		case WORKINGDIR:
			result = prime
					+ ((myObject == null) ? 0 : ((Repository) myObject)
							.getDirectory().hashCode());
			break;
		case REF:
		case TAG:
		case ADDITIONALREF:
			result = prime
					+ ((myObject == null) ? 0 : ((Ref) myObject).getName()
							.hashCode());
			break;
		case FILE:
		case FOLDER:
			result = prime
					+ ((myObject == null) ? 0 : ((File) myObject).getPath()
							.hashCode());
			break;
		case TAGS:
		case REMOTE:
		case PUSH:
		case FETCH:
		case BRANCHHIERARCHY:
		case STASHED_COMMIT:
		case ERROR:
			result = prime * result
					+ ((myObject == null) ? 0 : myObject.hashCode());

		}

		result = prime * result
				+ ((myParent == null) ? 0 : myParent.hashCode());
		result = prime
				+ ((myRepository == null) ? 0 : myRepository.getDirectory()
						.hashCode());
		result = prime * result + myType.hashCode();
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

		RepositoryTreeNode other = (RepositoryTreeNode) obj;

		if (myType == null) {
			if (other.myType != null)
				return false;
		} else if (!myType.equals(other.myType))
			return false;
		if (myParent == null) {
			if (other.myParent != null)
				return false;
		} else if (!myParent.equals(other.myParent))
			return false;
		if (myRepository == null) {
			if (other.myRepository != null) {
				return false;
			}
		} else {
			if (other.myRepository == null) {
				return false;
			}
			if (!myRepository.getDirectory()
					.equals(other.myRepository.getDirectory())) {
				return false;
			}
		}
		if (myObject == null) {
			if (other.myObject != null) {
				return false;
			}
		} else {
			if (other.myObject == null) {
				return false;
			}
			if (!checkObjectsEqual(other.myObject)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int compareTo(RepositoryTreeNode otherNode) {
		int typeDiff = this.myType.ordinal() - otherNode.getType().ordinal();
		if (typeDiff != 0)
			return typeDiff;


		switch (myType) {

		case BRANCHES:
		case LOCAL:
		case REMOTETRACKING:
		case BRANCHHIERARCHY:
			return CommonUtils.STRING_ASCENDING_COMPARATOR.compare(
					myObject.toString(), otherNode.getObject().toString());
		case REMOTES:
		case ADDITIONALREFS:
		case TAGS:
		case ERROR:
		case SUBMODULES:
		case STASH:
		case WORKINGDIR:
			return 0;

		case FETCH:
		case PUSH:
		case REMOTE:
			return CommonUtils.STRING_ASCENDING_COMPARATOR
					.compare((String) myObject, (String) otherNode.getObject());
		case FILE:
		case FOLDER:
			return CommonUtils.STRING_ASCENDING_COMPARATOR
					.compare(((File) myObject).getName(),
					((File) otherNode.getObject()).getName());
		case STASHED_COMMIT:
			return ((StashedCommitNode) this).getIndex()
					- ((StashedCommitNode) otherNode).getIndex();
		case TAG:
		case ADDITIONALREF:
		case REF:
			return CommonUtils.REF_ASCENDING_COMPARATOR.compare((Ref) myObject,
					(Ref) otherNode.getObject());
		case REPO:
			int nameCompare = CommonUtils.STRING_ASCENDING_COMPARATOR.compare(
					getDirectoryContainingRepo((Repository) myObject).getName(),
					getDirectoryContainingRepo(
							(Repository) otherNode.getObject())
									.getName());
			if (nameCompare != 0)
				return nameCompare;
			return CommonUtils.STRING_ASCENDING_COMPARATOR.compare(
					getDirectoryContainingRepo((Repository) myObject)
							.getParentFile().getPath(),
					getDirectoryContainingRepo(
							(Repository) otherNode.getObject()).getParentFile()
									.getPath());
		}
		return 0;
	}

	private File getDirectoryContainingRepo(Repository repo) {
		if (!repo.isBare())
			return repo.getDirectory().getParentFile();
		else
			return repo.getDirectory();
	}

	private boolean checkObjectsEqual(Object otherObject) {
		switch (myType) {
		case REPO:
		case REMOTES:
		case BRANCHES:
		case LOCAL:
		case REMOTETRACKING:
		case ADDITIONALREFS:
		case SUBMODULES:
		case STASH:
		case WORKINGDIR:
			return ((Repository) myObject).getDirectory().equals(
					((Repository) otherObject).getDirectory());
		case REF:
		case TAG:
		case ADDITIONALREF:
			return ((Ref) myObject).getName().equals(
					((Ref) otherObject).getName());
		case FOLDER:
		case FILE:
			return ((File) myObject).getPath().equals(
					((File) otherObject).getPath());
		case ERROR:
		case REMOTE:
		case FETCH:
		case PUSH:
		case BRANCHHIERARCHY:
		case STASHED_COMMIT:
		case TAGS:
			return myObject.equals(otherObject);
		}
		return false;
	}

	@Override
	public Object getAdapter(Class adapter) {
		if (Repository.class == adapter && myRepository != null) {
			return myRepository;
		}
		if (myObject != null) {
			if (adapter.isInstance(myObject)) {
				return myObject;
			}
		}
		return super.getAdapter(adapter);
	}

	@Override
	public String toString() {
		return "RepositoryNode[" + myType + ", " + myObject.toString() + "]";   //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$
	}
}
