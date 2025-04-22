package org.eclipse.ui.internal.navigator.extensions;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.internal.navigator.CommonNavigatorMessages;
import org.eclipse.ui.internal.navigator.NavigatorPlugin;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.INavigatorViewerDescriptor;
import org.eclipse.ui.navigator.MenuInsertionPoint;
import org.eclipse.ui.navigator.NavigatorActionService;

public final class NavigatorViewerDescriptor implements
		INavigatorViewerDescriptor {
	

	public static final String PROP_ENFORCE_HAS_CHILDREN = "org.eclipse.ui.navigator.enforceHasChildren"; //$NON-NLS-1$

	static final String TAG_INCLUDES = "includes"; //$NON-NLS-1$

	static final String TAG_EXCLUDES = "excludes"; //$NON-NLS-1$

	static final String ATT_IS_ROOT = "isRoot"; //$NON-NLS-1$

	static final String ATT_PATTERN = "pattern"; //$NON-NLS-1$

	private static final String TAG_CONTENT_EXTENSION = "contentExtension"; //$NON-NLS-1$

	private static final String TAG_ACTION_EXTENSION = "actionExtension"; //$NON-NLS-1$ 

	private final String viewerId;

	private String popupMenuId = null;

	private Binding actionBinding = new Binding(TAG_ACTION_EXTENSION);

	private Binding contentBinding = new Binding(TAG_CONTENT_EXTENSION);

	private MenuInsertionPoint[] customInsertionPoints = null;

	private boolean allowsPlatformContributions = true;

	private String inheritBindingsFromViewer;
	
	private String helpContext;
	
	private final Properties properties = new Properties();

	private Set dragAssistants; 

		super();
		this.viewerId = aViewerId;
	}

	@Override
	public String getViewerId() {
		return viewerId;
	}

	@Override
	public String getPopupMenuId() {
		return popupMenuId != null ? popupMenuId : viewerId;
	}

	public void consumeActionBinding(IConfigurationElement element) {
		consumeBinding(element, false);
	}

	public void consumeContentBinding(IConfigurationElement element) {
		consumeBinding(element, true);
	}

	@Override
	public boolean isRootExtension(String aContentExtensionId) {
		return contentBinding.isRootExtension(aContentExtensionId);
	}

	@Override
	public boolean allowsPlatformContributionsToContextMenu() {
		return allowsPlatformContributions;
	}

	@Override
	public boolean isVisibleContentExtension(String aContentExtensionId) {
		return contentBinding.isVisibleExtension(aContentExtensionId);
	}

	@Override
	public boolean isVisibleActionExtension(String anActionExtensionId) {
		return actionBinding.isVisibleExtension(anActionExtensionId);
	}

	@Override
	public boolean hasOverriddenRootExtensions() {
		return contentBinding.hasOverriddenRootExtensions();
	}

	@Override
	public MenuInsertionPoint[] getCustomInsertionPoints() {
		return customInsertionPoints;
	}

	public void setCustomInsertionPoints(
			MenuInsertionPoint[] newCustomInsertionPoints) {
		if (customInsertionPoints != null) {
			NavigatorPlugin
					.logError(
							0,
							"Attempt to override custom insertion points denied. Verify there are no colliding org.eclipse.ui.navigator.viewer extension points.", null); //$NON-NLS-1$
			return; // do not let them override the insertion points.
		}
		customInsertionPoints = newCustomInsertionPoints;
	}

	public void setAllowsPlatformContributions(
			boolean toAllowPlatformContributions) {
		allowsPlatformContributions = toAllowPlatformContributions;
	}

	public String getInheritBindingsFromViewer() {
		return inheritBindingsFromViewer;
	}
	
	public void setInheritBindingsFromViewer(String inherit) {
		inheritBindingsFromViewer = inherit;
	}
	
	@Override
	public String getHelpContext() {
		return helpContext;
	}

	public void setHelpContext(String context) {
		helpContext = context;
	}
	
	public void setContentBinding(Binding binding) {
		contentBinding = binding;
	}
	
	public Binding getContentBinding() {
		return contentBinding;
	}
	
	public void setActionBinding(Binding binding) {
		actionBinding = binding;
	}
	
	public Binding getActionBinding() {
		return actionBinding;
	}
	
	public void setDragAssistants(Set assistants) {
		dragAssistants = assistants;
	}
	

	
	@Override
	public String getStringConfigProperty(String aPropertyName) {
		return properties.getProperty(aPropertyName);
	}

	@Override
	public boolean getBooleanConfigProperty(String aPropertyName) {
		String propValue = properties.getProperty(aPropertyName);
		if (propValue == null) {
			return false;
		}
		return Boolean.valueOf(propValue).booleanValue();
	}
	 

		properties.setProperty(aPropertyName, aPropertyValue);
	}

	@Override
	public String toString() {
		return "ViewerDescriptor[" + viewerId + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}


		if (newPopupMenuId != null) {
			if (popupMenuId != null) {
				NavigatorPlugin
						.log(
								IStatus.WARNING,
								0,
								NLS
										.bind(
												CommonNavigatorMessages.NavigatorViewerDescriptor_Popup_Menu_Overridden,
												new Object[] { getViewerId(),
														popupMenuId,
														newPopupMenuId }), null);
			}
			popupMenuId = newPopupMenuId;
		}
	}

		getDragAssistants().add(descriptor);

	}

	public Set getDragAssistants() {
		if (dragAssistants == null) {
			dragAssistants = new HashSet();
		}
		return dragAssistants;
	}

	private void consumeBinding(IConfigurationElement element, boolean isContent) {
		IConfigurationElement[] includesElement = element
				.getChildren(TAG_INCLUDES);

		if (includesElement.length == 1) {
			if (isContent) {
				contentBinding.consumeIncludes(includesElement[0], true);
			} else {
				actionBinding.consumeIncludes(includesElement[0], false);
			}
		} else if (includesElement.length >= 1) {
			NavigatorPlugin.logError(0, NLS.bind(
					CommonNavigatorMessages.Too_many_elements_Warning,
					new Object[] {
							TAG_INCLUDES,
							element.getDeclaringExtension()
									.getUniqueIdentifier(),
							element.getDeclaringExtension().getNamespaceIdentifier() }),
					null);
		}

		IConfigurationElement[] excludesElement = element
				.getChildren(TAG_EXCLUDES);

		if (excludesElement.length == 1) {

			if (isContent) {
				contentBinding.consumeExcludes(excludesElement[0]);
			} else {
				actionBinding.consumeExcludes(excludesElement[0]);
			}
		} else if (excludesElement.length >= 1) {
			NavigatorPlugin.logError(0, NLS.bind(
					CommonNavigatorMessages.Too_many_elements_Warning,
					new Object[] {
							TAG_EXCLUDES,
							element.getDeclaringExtension()
									.getUniqueIdentifier(),
							element.getDeclaringExtension().getNamespaceIdentifier() }),
					null);
		}
	}
	
	void updateFromParent(NavigatorViewerDescriptor parent) {
		getActionBinding().addBinding(parent.getActionBinding());
		getContentBinding().addBinding(parent.getContentBinding());
		getDragAssistants().addAll(parent.getDragAssistants());
	}

}
