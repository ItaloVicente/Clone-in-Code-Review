
package org.eclipse.ui.views.properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.views.properties.PropertiesMessages;

public class PinPropertySheetAction extends Action {

	public PinPropertySheetAction() {
		super(PropertiesMessages.Pin_text, IAction.AS_CHECK_BOX);

		setId(PinPropertySheetAction.class.getName()
				+ "#" + Long.toString(System.currentTimeMillis())); //$NON-NLS-1$
		setToolTipText(PropertiesMessages.Pin_toolTip);
		setImageDescriptor(WorkbenchImages
				.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_PIN_EDITOR));
		setDisabledImageDescriptor(WorkbenchImages
				.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_PIN_EDITOR_DISABLED));

		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IPropertiesHelpContextIds.PIN_ACTION);
	}
}
