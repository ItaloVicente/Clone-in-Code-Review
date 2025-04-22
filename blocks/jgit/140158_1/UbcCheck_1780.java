
package org.eclipse.jgit.util.sha1;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;

public class Sha1CollisionException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public Sha1CollisionException(ObjectId id) {
		super(MessageFormat.format(
				JGitText.get().sha1CollisionDetected
				id.name()));
	}
}
