package org.eclipse.jgit.api.errors;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.patch.FormatError;

public class PatchFormatException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	private List<FormatError> errors;

	public PatchFormatException(List<FormatError> errors) {
		super(MessageFormat.format(JGitText.get().patchFormatException
		this.errors = errors;
	}

	public List<FormatError> getErrors() {
		return errors;
	}

}
