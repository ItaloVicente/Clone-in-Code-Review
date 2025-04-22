package org.eclipse.egit.gitflow.ui.internal.validation;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.egit.gitflow.BranchNameValidator;
import org.eclipse.egit.gitflow.ui.internal.UIText;
import org.eclipse.jface.dialogs.IInputValidator;

abstract public class NameValidator implements IInputValidator {
	@Override
	public String isValid(String newText) {
		try {
			if (branchExists(newText)) {
				return String.format(UIText.NameValidator_nameAlreadyExists,
						newText);
			}
			if (!BranchNameValidator.isBranchNameValid(newText)) {
				return String.format(UIText.NameValidator_invalidName, newText,
						BranchNameValidator.ILLEGAL_CHARS);
			}
		} catch (CoreException e) {
			return null;
		}
		return null;
	}

	abstract protected boolean branchExists(String newText)
			throws CoreException;
}
