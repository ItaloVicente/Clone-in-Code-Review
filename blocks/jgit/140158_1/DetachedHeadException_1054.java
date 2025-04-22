package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;

public class ConcurrentRefUpdateException extends GitAPIException {
	private static final long serialVersionUID = 1L;
	private RefUpdate.Result rc;
	private Ref ref;

	public ConcurrentRefUpdateException(String message
			RefUpdate.Result rc
				+ MessageFormat.format(JGitText.get().refUpdateReturnCodeWas
		this.rc = rc;
		this.ref = ref;
	}

	public ConcurrentRefUpdateException(String message
			RefUpdate.Result rc) {
				+ MessageFormat.format(JGitText.get().refUpdateReturnCodeWas
		this.rc = rc;
		this.ref = ref;
	}

	public Ref getRef() {
		return ref;
	}

	public RefUpdate.Result getResult() {
		return rc;
	}
}
