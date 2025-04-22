
package org.eclipse.jgit.errors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.internal.JGitText;

public class CompoundException extends Exception {
	private static final long serialVersionUID = 1L;

	private static String format(Collection<Throwable> causes) {
		final StringBuilder msg = new StringBuilder();
		msg.append(JGitText.get().failureDueToOneOfTheFollowing);
		for (Throwable c : causes) {
			msg.append(c.getMessage());
		}
		return msg.toString();
	}

	private final List<Throwable> causeList;

	public CompoundException(Collection<Throwable> why) {
		super(format(why));
		causeList = Collections.unmodifiableList(new ArrayList<>(why));
	}

	public List<Throwable> getAllCauses() {
		return causeList;
	}
}
