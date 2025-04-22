package org.eclipse.ui.navigator;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.Separator;

public interface INavigatorViewerDescriptor {

	String PROP_HIDE_AVAILABLE_EXT_TAB = "org.eclipse.ui.navigator.hideAvailableExtensionsTab"; //$NON-NLS-1$

	String PROP_HIDE_AVAILABLE_CUSTOMIZATIONS_DIALOG = "org.eclipse.ui.navigator.hideAvailableCustomizationsDialog"; //$NON-NLS-1$

	String PROP_HIDE_COLLAPSE_ALL_ACTION = "org.eclipse.ui.navigator.hideCollapseAllAction"; //$NON-NLS-1$

	String PROP_HIDE_LINK_WITH_EDITOR_ACTION = "org.eclipse.ui.navigator.hideLinkWithEditorAction"; //$NON-NLS-1$

	String PROP_CUSTOMIZE_VIEW_DIALOG_HELP_CONTEXT = "org.eclipse.ui.navigator.customizeViewDialogHelpContext"; //$NON-NLS-1$

	String getViewerId();

	String getPopupMenuId();

	boolean isVisibleContentExtension(String aContentExtensionId);

	boolean isVisibleActionExtension(String anActionExtensionId);

	boolean isRootExtension(String aContentExtensionId);

	boolean hasOverriddenRootExtensions();

	boolean allowsPlatformContributionsToContextMenu();

	MenuInsertionPoint[] getCustomInsertionPoints();

	String getStringConfigProperty(String aPropertyName);

	boolean getBooleanConfigProperty(String aPropertyName);
	
	String getHelpContext();

}
