
package org.eclipse.jgit.errors;

import java.util.Map;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.transport.URIish;

public class MissingBundlePrerequisiteException extends TransportException {
	private static final long serialVersionUID = 1L;

	private static String format(Map<ObjectId
		final StringBuilder r = new StringBuilder();
		r.append(JGitText.get().missingPrerequisiteCommits);
		for (Map.Entry<ObjectId
			r.append(e.getKey().name());
			if (e.getValue() != null)
		}
		return r.toString();
	}

	public MissingBundlePrerequisiteException(final URIish uri
			final Map<ObjectId
		super(uri
	}
}
