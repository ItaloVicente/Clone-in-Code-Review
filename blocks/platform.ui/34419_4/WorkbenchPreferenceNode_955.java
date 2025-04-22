package org.eclipse.ui.internal.dialogs;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.misc.StatusUtil;
import org.eclipse.ui.internal.preferences.WorkbenchPreferenceExpressionNode;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.internal.registry.PreferencePageRegistryReader;

public class WorkbenchPreferenceManager extends PreferenceManager implements
		IExtensionChangeHandler {

	public WorkbenchPreferenceManager(char separatorChar) {
		super(separatorChar, new WorkbenchPreferenceExpressionNode("")); //$NON-NLS-1$
        
		IExtensionTracker tracker = PlatformUI.getWorkbench().getExtensionTracker();
		tracker.registerHandler(this, ExtensionTracker.createExtensionPointFilter(getExtensionPointFilter()));

		Platform.getExtensionRegistry().addRegistryChangeListener(
				new IRegistryChangeListener() {

					@Override
					public void registryChanged(IRegistryChangeEvent event) {
						if (event.getExtensionDeltas(PlatformUI.PLUGIN_ID,
								IWorkbenchRegistryConstants.PL_KEYWORDS).length > 0) {
							for (Iterator j = getElements(
									PreferenceManager.POST_ORDER).iterator(); j
									.hasNext();) {
								((WorkbenchPreferenceNode) j.next())
										.clearKeywords();
							}
						}
					}
				});
	}

	public void addPages(Collection pageContributions) {

		Iterator iterator = pageContributions.iterator();
		while (iterator.hasNext()) {
			Object next = iterator.next();
			if (next instanceof WorkbenchPreferenceNode) {
				WorkbenchPreferenceNode wNode = (WorkbenchPreferenceNode) next;
				addToRoot(wNode);
				registerNode(wNode);
			}
		}

	}

	private void registerNode(WorkbenchPreferenceNode node) {
		PlatformUI.getWorkbench().getExtensionTracker().registerObject(
				node.getConfigurationElement().getDeclaringExtension(), node,
				IExtensionTracker.REF_WEAK);
		IPreferenceNode[] subNodes = node.getSubNodes();
		for (int i = 0; i < subNodes.length; i++) {
			registerNode((WorkbenchPreferenceNode) subNodes[i]);
		}

	}

	@Override
	public void addExtension(IExtensionTracker tracker, IExtension extension) {
		IConfigurationElement[] elements = extension.getConfigurationElements();
		for (int i = 0; i < elements.length; i++) {
			WorkbenchPreferenceNode node = PreferencePageRegistryReader
					.createNode(elements[i]);
			if (node == null) {
				continue;
			}
			registerNode(node);
			String category = node.getCategory();
			if (category == null) {
				addToRoot(node);
			} else {
				IPreferenceNode parent = null;
				for (Iterator j = getElements(PreferenceManager.POST_ORDER)
						.iterator(); j.hasNext();) {
					IPreferenceNode element = (IPreferenceNode) j
							.next();
					if (category.equals(element.getId())) {
						parent = element;
						break;
					}
				}
				if (parent == null) {
					String message = "Invalid preference category path: " + category + " (bundle: " + node.getPluginId() + ", page: " + node.getId() + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ 
					WorkbenchPlugin.log(StatusUtil.newStatus(IStatus.WARNING, message, null));
					addToRoot(node);
				} else {
					parent.add(node);
				}
			}
		}
	}

	private IExtensionPoint getExtensionPointFilter() {
		return Platform.getExtensionRegistry().getExtensionPoint(
				PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_PREFERENCES);
	}

	@Override
	public void removeExtension(IExtension extension, Object[] objects) {
		for (int i = 0; i < objects.length; i++) {
			if (objects[i] instanceof IPreferenceNode) {
				IPreferenceNode wNode = (IPreferenceNode) objects[i];
				wNode.disposeResources();
				deepRemove(getRoot(), wNode);
			}
		}
	}

	private boolean deepRemove(IPreferenceNode parent,
			IPreferenceNode nodeToRemove) {
		if (parent == nodeToRemove) {
			if (parent == getRoot()) {
				removeAll(); // we're removing the root
				return true;
			}
		}

		if (parent.remove(nodeToRemove)) {
			return true;
		}

		IPreferenceNode[] subNodes = parent.getSubNodes();
		for (int i = 0; i < subNodes.length; i++) {
			if (deepRemove(subNodes[i], nodeToRemove)) {
				return true;
			}
		}
		return false;
	}
}
