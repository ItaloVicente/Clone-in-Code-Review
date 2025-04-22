package org.eclipse.ui;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferencePage;

public interface IWorkbenchPropertyPageMulti extends IPreferencePage {

	public void setElements(IAdaptable[] elements);

}
