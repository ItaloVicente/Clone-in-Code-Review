
package org.eclipse.jgit.events;

import java.io.File;
import java.util.Collection;

public class DotFileChangedEvent
		extends RepositoryEvent<DotFileChangedListener> {

	private final Collection<File> files;

	public DotFileChangedEvent(Collection<File> files) {
		this.files = files;
	}

	public Collection<File> getFiles() {
		return files;
	}

	@Override
	public Class<DotFileChangedListener> getListenerType() {
		return DotFileChangedListener.class;
	}

	@Override
	public void dispatch(DotFileChangedListener listener) {
		listener.onDotFileChanged(this);
	}
}
