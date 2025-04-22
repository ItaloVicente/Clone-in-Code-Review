
package org.eclipse.jgit.errors;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class EntryExistsException extends IOException {
	private static final long serialVersionUID = 1L;

	public EntryExistsException(String name) {
		super(MessageFormat.format(JGitText.get().treeEntryAlreadyExists
	}
}
