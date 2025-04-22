package org.eclipse.e4.ui.workbench.swt;


public interface ISaveablesSource {

	Saveable[] getSaveables();

	Saveable[] getActiveSaveables();
}
