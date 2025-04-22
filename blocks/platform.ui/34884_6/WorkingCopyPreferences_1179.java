
package org.eclipse.ui.internal.preferences;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.internal.WorkbenchMessages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.preferences.SettingsTransfer;

public abstract class WorkbenchSettingsTransfer extends SettingsTransfer {

	protected IStatus noWorkingSettingsStatus() {
		return new Status(IStatus.ERROR, WorkbenchPlugin.PI_WORKBENCH,
				WorkbenchMessages.WorkbenchSettings_CouldNotFindLocation);
	}

	protected IPath getNewWorkbenchStateLocation(IPath newWorkspaceRoot) {
		IPath currentWorkspaceRoot = Platform.getLocation();
	
		IPath dataLocation = WorkbenchPlugin.getDefault().getDataLocation();
	
		if (dataLocation == null)
			return null;
		int segmentsToRemove = dataLocation
				.matchingFirstSegments(currentWorkspaceRoot);
	
		dataLocation = dataLocation.removeFirstSegments(segmentsToRemove);
		dataLocation = newWorkspaceRoot.append(dataLocation);
		return dataLocation;
	}

}
