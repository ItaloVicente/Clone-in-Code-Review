package org.eclipse.jgit.niofs.internal.op.exceptions;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;

public class ConcurrentRefUpdateException extends GitException {

	private RefUpdate.Result rc;
	private Ref ref;

	public ConcurrentRefUpdateException(final String message
		super(rc == null ? message
				: message + ". " + MessageFormat.format(JGitText.get().refUpdateReturnCodeWas
		this.rc = rc;
		this.ref = ref;
	}
}
