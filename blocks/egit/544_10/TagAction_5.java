
package org.eclipse.egit.ui.internal;

import java.io.IOException;

import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIText;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.osgi.util.NLS;

public class ValidationUtils {

	public static IInputValidator getRefNameInputValidator(final Repository repo, final String refPrefix) {
		return new IInputValidator() {
			public String isValid(String newText) {
				if (newText.length() == 0) {
					return ""; //$NON-NLS-1$
				}

				String testFor = refPrefix + newText;
				try {
					if (repo.resolve(testFor) != null)
						return UIText.BranchSelectionDialog_ErrorAlreadyExists;
				} catch (IOException e1) {
					Activator.logError(NLS.bind(
							UIText.BranchSelectionDialog_ErrorCouldNotResolve, testFor), e1);
					return e1.getMessage();
				}
				if (!Repository.isValidRefName(testFor))
					return UIText.BranchSelectionDialog_ErrorInvalidRefName;
				return null;
			}
		};
	}

}
