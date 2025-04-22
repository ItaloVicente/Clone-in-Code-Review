
package org.eclipse.jgit.errors;

import org.eclipse.jgit.lib.ObjectToPack;

public class StoredObjectRepresentationNotAvailableException extends Exception {
	private static final long serialVersionUID = 1L;

	public StoredObjectRepresentationNotAvailableException(ObjectToPack otp) {
	}
}
