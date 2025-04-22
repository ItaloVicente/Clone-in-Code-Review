package org.eclipse.jgit.lib;

/**
 * A default {@link RepositoryListener} that does nothing except invoke an
 * optional general method for any repository change.
 */
public class RepositoryAdapter implements RepositoryListener {

	public void indexChanged(final IndexChangedEvent e) {
	}

	public void refsChanged(final RefsChangedEvent e) {
	}
