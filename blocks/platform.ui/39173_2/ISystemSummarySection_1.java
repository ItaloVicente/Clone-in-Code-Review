
package org.eclipse.e4.ui.about;

import org.eclipse.swt.widgets.Button;

public interface IInstallationPageContainer {

	public void registerPageButton(InstallationPage page, Button button);

	public void closeModalContainers();

}
