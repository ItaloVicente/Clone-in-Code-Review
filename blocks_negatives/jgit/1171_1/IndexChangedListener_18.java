package org.eclipse.jgit.lib;

/**
 * This class passes information about changed refs to a
 * {@link RepositoryListener}
 *
 * Currently only a reference to the repository is passed.
 */
public class RepositoryChangedEvent {
	private final Repository repository;

	RepositoryChangedEvent(final Repository repository) {
		this.repository = repository;
	}
