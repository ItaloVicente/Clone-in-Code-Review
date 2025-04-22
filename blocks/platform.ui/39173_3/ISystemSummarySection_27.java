
package org.eclipse.e4.ui.workbench.swt.dialog;

import org.eclipse.swt.widgets.Button;

public interface IInstallationPageContainer {

	public void registerPageButton(InstallationPage page, Button button);

	public void closeModalContainers();

}
