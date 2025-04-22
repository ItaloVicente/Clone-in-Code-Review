package org.eclipse.ui.dialogs;

import org.eclipse.core.runtime.IStatus;

public interface ISelectionStatusValidator {

    IStatus validate(Object[] selection);

}
