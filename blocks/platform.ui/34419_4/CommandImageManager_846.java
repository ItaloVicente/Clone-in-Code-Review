package org.eclipse.ui.internal.browser;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.AbstractWorkbenchBrowserSupport;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class WorkbenchBrowserSupport extends AbstractWorkbenchBrowserSupport {

	private static WorkbenchBrowserSupport instance;

	private IWorkbenchBrowserSupport activeSupport;

	private boolean initialized;
    
    private String desiredBrowserSupportId;

	private IExtensionChangeHandler handler = new IExtensionChangeHandler() {
        
        @Override
		public void addExtension(IExtensionTracker tracker,IExtension extension) {
        }

        @Override
		public void removeExtension(IExtension source, Object[] objects) {
			for (int i = 0; i < objects.length; i++) {
				if (objects[i] == activeSupport) {
					dispose();
					PlatformUI.getWorkbench().getExtensionTracker()
							.unregisterHandler(handler);
				}
			}
		}
	};

	private WorkbenchBrowserSupport() {
	}

	public static IWorkbenchBrowserSupport getInstance() {
		if (instance == null) {
			instance = new WorkbenchBrowserSupport();
		}
		return instance;
	}

	@Override
	public IWebBrowser createBrowser(int style, String browserId, String name,
			String tooltip) throws PartInitException {
		return getActiveSupport()
				.createBrowser(style, browserId, name, tooltip);
	}

	@Override
	public IWebBrowser createBrowser(String browserId) throws PartInitException {
		return getActiveSupport().createBrowser(browserId);
	}
	
	@Override
	public boolean isInternalWebBrowserAvailable() {
		return getActiveSupport().isInternalWebBrowserAvailable();
	}

	private IWorkbenchBrowserSupport getActiveSupport() {
		if (initialized == false) {
			loadActiveSupport();
		}
		if (activeSupport == null) {
			activeSupport = new DefaultWorkbenchBrowserSupport();
		}
		return activeSupport;
	}
    
    public boolean hasNonDefaultBrowser() {
        return !(getActiveSupport() instanceof DefaultWorkbenchBrowserSupport);
    }

	private void loadActiveSupport() {
		BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {
			@Override
			public void run() {
                IConfigurationElement[] elements = Platform
                        .getExtensionRegistry().getConfigurationElementsFor(
                                PlatformUI.PLUGIN_ID,
                                IWorkbenchRegistryConstants.PL_BROWSER_SUPPORT);
				IConfigurationElement elementToUse = null;
                
                if (desiredBrowserSupportId != null) {
					elementToUse = findDesiredElement(elements);
				} else {
					elementToUse = getElementToUse(elements);
				}
				if (elementToUse != null) {
					initialized = initializePluggableBrowserSupport(elementToUse);
				}
			}

            private IConfigurationElement findDesiredElement(IConfigurationElement [] elements) {
                for (int i = 0; i < elements.length; i++) {
                    if (desiredBrowserSupportId.equals(elements[i].getDeclaringExtension().getUniqueIdentifier())) {
						return elements[i];
					}
                }
                return null;
            }

            private IExtensionPoint getExtensionPoint() {
                return Platform.getExtensionRegistry()
						.getExtensionPoint(PlatformUI.PLUGIN_ID, IWorkbenchRegistryConstants.PL_BROWSER_SUPPORT);
            }

			private IConfigurationElement getElementToUse(
					IConfigurationElement[] elements) {
				if (elements.length == 0) {
					return null;
				}
				IConfigurationElement defaultElement = null;
				IConfigurationElement choice = null;
				for (int i = 0; i < elements.length; i++) {
					IConfigurationElement element = elements[i];
					if (element.getName().equals(IWorkbenchRegistryConstants.TAG_SUPPORT)) {
						String def = element.getAttribute(IWorkbenchRegistryConstants.ATT_DEFAULT);
						if (def != null && Boolean.valueOf(def).booleanValue()) { 
							if (defaultElement == null) {
								defaultElement = element;
							}
						} else {
							if (choice == null) {
								choice = element;
							}
						}
					}
				}
				if (choice == null) {
					choice = defaultElement;
				}
				return choice;
			}

			private boolean initializePluggableBrowserSupport(
					IConfigurationElement element) {
				try {
					activeSupport = (AbstractWorkbenchBrowserSupport) WorkbenchPlugin
							.createExtension(element, IWorkbenchRegistryConstants.ATT_CLASS);
					IExtensionTracker extensionTracker = PlatformUI.getWorkbench().getExtensionTracker();
                    extensionTracker.registerHandler(handler, ExtensionTracker
                            .createExtensionPointFilter(getExtensionPoint()));
					extensionTracker
							.registerObject(element.getDeclaringExtension(),
									activeSupport, IExtensionTracker.REF_WEAK);
					return true;
				} catch (CoreException e) {
					WorkbenchPlugin
							.log("Unable to instantiate browser support" + e.getStatus(), e);//$NON-NLS-1$
				}
				return false;
			}

		});
	}
    
    public void setDesiredBrowserSupportId(String desiredBrowserSupportId) {
        dispose(); // prep for a new help system
        this.desiredBrowserSupportId = desiredBrowserSupportId;
    }

    protected void dispose() {
        activeSupport = null;
        initialized = false;
    }
}
