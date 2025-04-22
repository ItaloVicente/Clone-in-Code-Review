
package org.eclipse.jgit.storage.dfs;

public class DfsRepositoryDescription {
	private final String repositoryName;

	public DfsRepositoryDescription() {
		this(null);
	}

	public DfsRepositoryDescription(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	@Override
	public int hashCode() {
		if (getRepositoryName() != null)
			return getRepositoryName().hashCode();
		return System.identityHashCode(this);
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof DfsRepositoryDescription){
			String name = getRepositoryName();
			String otherName = ((DfsRepositoryDescription) b).getRepositoryName();
			return name != null ? name.equals(otherName) : this == b;
		}
		return false;
	}

	@Override
	public String toString() {
		return "DfsRepositoryDescription[" + getRepositoryName() + "]";
	}
}
