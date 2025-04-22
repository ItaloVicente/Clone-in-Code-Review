package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;

public class ConcurrentRefUpdateException extends GitAPIException {
	private static final long serialVersionUID = 1L;
	private RefUpdate.Result rc;
	private Ref ref;

	ConcurrentRefUpdateException(String message
			RefUpdate.Result rc
		super((rc == null) ? message : message
				+ ". RefUpdate return code was: " + rc
		this.rc = rc;
		this.ref = ref;
	}

	ConcurrentRefUpdateException(String message
			RefUpdate.Result rc) {
		super((rc == null) ? message : message
				+ ". RefUpdate return code was: " + rc);
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
