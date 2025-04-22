
package org.eclipse.ui.menus;

import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.services.IServiceLocator;

public class CommandContributionItemParameter {
	public IServiceLocator serviceLocator;

	public String id;

	public String commandId;

	public Map parameters;

	public ImageDescriptor icon;

	public ImageDescriptor disabledIcon;

	public ImageDescriptor hoverIcon;

	public String label;

	public String mnemonic;

	public String tooltip;

	public int style;

	public String helpContextId;

	public String iconStyle;

	public boolean visibleEnabled;

	public int mode;

	public CommandContributionItemParameter(IServiceLocator serviceLocator,
			String id, String commandId, int style) {
		this.serviceLocator = serviceLocator;
		this.id = id;
		this.commandId = commandId;
		this.style = style;
	}

	public CommandContributionItemParameter(IServiceLocator serviceLocator,
			String id, String commandId, Map parameters, ImageDescriptor icon,
			ImageDescriptor disabledIcon, ImageDescriptor hoverIcon,
			String label, String mnemonic, String tooltip, int style,
			String helpContextId, boolean visibleEnabled) {
		this.serviceLocator = serviceLocator;
		this.id = id;
		this.commandId = commandId;
		this.parameters = parameters;
		this.icon = icon;
		this.disabledIcon = disabledIcon;
		this.hoverIcon = hoverIcon;
		this.label = label;
		this.mnemonic = mnemonic;
		this.tooltip = tooltip;
		this.style = style;
		this.helpContextId = helpContextId;
		this.visibleEnabled = visibleEnabled;
	}
}
