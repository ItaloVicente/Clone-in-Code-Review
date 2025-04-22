
package org.eclipse.jgit.errors;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class RepositoryNotFoundException extends TransportException {
	private static final long serialVersionUID = 1L;

	public RepositoryNotFoundException(File location) {
		this(location.getPath());
	}

	public RepositoryNotFoundException(File location
		this(location.getPath()
	}

	public RepositoryNotFoundException(String location) {
		super(message(location));
	}

	public RepositoryNotFoundException(String location
		super(message(location)
	}

	private static String message(String location) {
		return MessageFormat.format(JGitText.get().repositoryNotFound
	}
}
