
package org.eclipse.jgit.events;

import org.eclipse.jgit.lib.Repository;

public abstract class RepositoryEvent<T extends RepositoryListener> {
	private Repository repository;

	public void setRepository(Repository r) {
		if (repository == null)
			repository = r;
	}

	public Repository getRepository() {
		return repository;
	}

	public abstract Class<T> getListenerType();

	public abstract void dispatch(T listener);

	@Override
	public String toString() {
		String type = getClass().getSimpleName();
		if (repository == null)
			return type;
		return type + "[" + repository + "]";
	}
}
