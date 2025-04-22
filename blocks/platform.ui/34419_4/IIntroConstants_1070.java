package org.eclipse.ui.internal.help;

import java.net.URL;
import java.util.Hashtable;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.help.HelpSystem;
import org.eclipse.help.IContext;
import org.eclipse.help.IContext2;
import org.eclipse.help.IHelp;
import org.eclipse.help.IHelpResource;
import org.eclipse.help.IToc;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.LegacyActionTools;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.HelpEvent;
import org.eclipse.swt.events.HelpListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommand;
import org.eclipse.ui.help.AbstractHelpUI;
import org.eclipse.ui.help.IContextComputer;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.internal.IWorkbenchHelpContextIds;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public final class WorkbenchHelpSystem implements IWorkbenchHelpSystem {

	public static final String HELP_KEY = "org.eclipse.ui.help";//$NON-NLS-1$

	private static final String HELP_SYSTEM_EXTENSION_ID = PlatformUI.PLUGIN_ID + '.' + IWorkbenchRegistryConstants.PL_HELPSUPPORT;

	private static final String HELP_SYSTEM_CLASS_ATTRIBUTE = "class";//$NON-NLS-1$

	private static WorkbenchHelpSystem instance;

	private static class WorkbenchHelpListener implements HelpListener {
		@Override
		public void helpRequested(HelpEvent event) {

			if (getInstance().getHelpUI() == null) {
				return;
			}

			Object object = event.widget.getData(HELP_KEY);

			IContext context = null;
			if (object instanceof String) {
				context = HelpSystem.getContext((String) object);
			} else if (object instanceof IContext) {
				context = (IContext) object;
			} else if (object instanceof IContextComputer) {
				Object[] helpContexts = ((IContextComputer) object)
						.computeContexts(event);
				if (helpContexts != null && helpContexts.length > 0) {
					Object primaryEntry = helpContexts[0];
					if (primaryEntry instanceof String) {
						context = HelpSystem.getContext((String) primaryEntry);
					} else if (primaryEntry instanceof IContext) {
						context = (IContext) primaryEntry;
					}
				}
			} else if (object instanceof Object[]) {
				Object[] helpContexts = (Object[]) object;
				if (helpContexts.length > 0) {
					Object primaryEntry = helpContexts[0];
					if (primaryEntry instanceof String) {
						context = HelpSystem.getContext((String) primaryEntry);
					} else if (primaryEntry instanceof IContext) {
						context = (IContext) primaryEntry;
					}
				}
			}
			
			if (context == null) {
				context = HelpSystem.getContext(IWorkbenchHelpContextIds.MISSING);
			}
			
			if (context != null) {
				Point point = computePopUpLocation(event.widget.getDisplay());
				getInstance().displayContext(context, point.x, point.y);
			}
		}
	}

	private boolean isInitialized;

	private AbstractHelpUI pluggableHelpUI = null;

	private String desiredHelpSystemId;

	private Hashtable registeredIDTable;

    private IExtensionChangeHandler handler = new IExtensionChangeHandler() {

    	@Override
		public void addExtension(IExtensionTracker tracker,IExtension extension) {
        }

        @Override
		public void removeExtension(IExtension source, Object[] objects) {
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] == pluggableHelpUI) {
                    isInitialized = false;
                    pluggableHelpUI = null;
                    helpCompatibilityWrapper = null;
                    PlatformUI.getWorkbench().getExtensionTracker()
							.unregisterHandler(handler);
                }
            }
        }
    };
    
	private class CompatibilityIHelpImplementation implements IHelp {

		@Deprecated
		@Override
		public void displayHelp() {
			AbstractHelpUI helpUI = getHelpUI();
			if (helpUI != null) {
				helpUI.displayHelp();
			}
		}

		@Deprecated
		@Override
		public void displayContext(IContext context, int x, int y) {
			AbstractHelpUI helpUI = getHelpUI();
			if (helpUI != null) {
				helpUI.displayContext(context, x, y);
			}
		}

		@Deprecated
		@Override
		public void displayContext(String contextId, int x, int y) {
			IContext context = HelpSystem.getContext(contextId);
			if (context != null) {
				displayContext(context, x, y);
			}
		}

		@Deprecated
		@Override
		public void displayHelpResource(String href) {
			AbstractHelpUI helpUI = getHelpUI();
			if (helpUI != null) {
				helpUI.displayHelpResource(href);
			}
		}

		@Deprecated
		@Override
		public void displayHelpResource(IHelpResource helpResource) {
			displayHelpResource(helpResource.getHref());
		}

		@Deprecated
		@Override
		public void displayHelp(String toc) {
			displayHelpResource(toc);
		}

		@Deprecated
		@Override
		public void displayHelp(String toc, String selectedTopic) {
			displayHelpResource(selectedTopic);
		}

		@Deprecated
		@Override
		public void displayHelp(String contextId, int x, int y) {
			displayContext(contextId, x, y);
		}

		@Deprecated
		@Override
		public void displayHelp(IContext context, int x, int y) {
			displayContext(context, x, y);
		}

		@Deprecated
		@Override
		public IContext getContext(String contextId) {
			return HelpSystem.getContext(contextId);
		}

		@Deprecated
		@Override
		public IToc[] getTocs() {
			return HelpSystem.getTocs();
		}

		@Deprecated
		@Override
		public boolean isContextHelpDisplayed() {
			return isContextHelpDisplayed();
		}
	}

	private static class ContextWithTitle implements IContext2 {
		private IContext context;
		private String title;

		ContextWithTitle(IContext context, String title) {
			this.context = context;
			this.title = title;
		}

		@Override
		public String getTitle() {
			if (context instanceof IContext2) {
				String ctitle = ((IContext2)context).getTitle();
				if (ctitle!=null) {
					return ctitle;
				}
			}
			return title;
		}

		@Override
		public String getStyledText() {
			if (context instanceof IContext2) {
				return ((IContext2)context).getStyledText();
			}
			return context.getText();
		}

		@Override
		public String getCategory(IHelpResource topic) {
			if (context instanceof IContext2) {
				return ((IContext2)context).getCategory(topic);
			}
			return null;
		}

		@Override
		public IHelpResource[] getRelatedTopics() {
			return context.getRelatedTopics();
		}

		@Override
		public String getText() {
			return context.getText();
		}
	}
	
	private IHelp helpCompatibilityWrapper = null;

	private static HelpListener helpListener;

	public String getDesiredHelpSystemId() {
		return desiredHelpSystemId;
	}
	
	public void setDesiredHelpSystemId(String desiredHelpSystemId) {
		dispose(); // prep for a new help system
		this.desiredHelpSystemId = desiredHelpSystemId;
	}
	
	private WorkbenchHelpSystem() {
	}

	public static WorkbenchHelpSystem getInstance() {
		if (instance == null) {
			instance = new WorkbenchHelpSystem();
		}

		return instance;
	}

	public static void disposeIfNecessary() {
		if (instance != null) {
			instance.dispose();
			instance = null;
		}
	}

	public void dispose() {
		pluggableHelpUI = null;
		helpCompatibilityWrapper = null;
		isInitialized = false;
		PlatformUI.getWorkbench().getExtensionTracker()
				.unregisterHandler(handler);
	}

	private AbstractHelpUI getHelpUI() {
		if (!isInitialized) {
			isInitialized = initializePluggableHelpUI();
		}
		return pluggableHelpUI;
	}

	private boolean initializePluggableHelpUI() {
		final boolean[] ret = new boolean[] { false };

		BusyIndicator.showWhile(Display.getCurrent(), new Runnable() {

			@Override
			public void run() {
				IExtensionPoint point = Platform.getExtensionRegistry()
						.getExtensionPoint(HELP_SYSTEM_EXTENSION_ID);
				if (point == null) {
					return;
				}
				IExtension[] extensions = point.getExtensions();
				if (extensions.length == 0) {
					return;
				}

				IConfigurationElement elementToUse = null;
				if (desiredHelpSystemId == null) {
					elementToUse = getFirstElement(extensions);
				} else {
					elementToUse = findElement(desiredHelpSystemId, extensions);
				}

				if (elementToUse != null) {
					ret[0] = initializePluggableHelpUI(elementToUse);
				}
			}

			private IConfigurationElement findElement(
					String desiredHelpSystemId, IExtension[] extensions) {
				for (int i = 0; i < extensions.length; i++) {
					IExtension extension = extensions[i];
					if (desiredHelpSystemId.equals(extension.getUniqueIdentifier())) {
						IConfigurationElement[] elements = extension
								.getConfigurationElements();
						if (elements.length == 0) {
							return null;
						}
						return elements[0];
					}

				}
				return null;
			}

			private IConfigurationElement getFirstElement(
					IExtension[] extensions) {
				IConfigurationElement[] elements = extensions[0]
						.getConfigurationElements();
				if (elements.length == 0) {
					return null;
				}
				return elements[0];
			}

			private boolean initializePluggableHelpUI(
					IConfigurationElement element) {
				try {
					pluggableHelpUI = (AbstractHelpUI) WorkbenchPlugin
							.createExtension(element,
									HELP_SYSTEM_CLASS_ATTRIBUTE);
					PlatformUI.getWorkbench().getExtensionTracker()
							.registerHandler(handler, null);
					PlatformUI
							.getWorkbench()
							.getExtensionTracker()
							.registerObject(element.getDeclaringExtension(),
									pluggableHelpUI, IExtensionTracker.REF_WEAK);
					return true;
				} catch (CoreException e) {
					WorkbenchPlugin.log(
							"Unable to instantiate help UI" + e.getStatus(), e);//$NON-NLS-1$
				}
				return false;
			}

		});
		return ret[0];
	}

	private static Point computePopUpLocation(Display display) {
		Point point = display.getCursorLocation();
		return new Point(point.x + 15, point.y);
	}

	private HelpListener getHelpListener() {
		if (helpListener == null) {
			helpListener = new WorkbenchHelpListener();
		}
		return helpListener;
	}

	@Deprecated
	public IHelp getHelpSupport() {
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null && helpCompatibilityWrapper == null) {
			helpCompatibilityWrapper = new CompatibilityIHelpImplementation();
		}
		return helpCompatibilityWrapper;

	}

	@Deprecated
	public void setHelp(IAction action, final Object[] contexts) {
		for (int i = 0; i < contexts.length; i++) {
			Assert.isTrue(contexts[i] instanceof String
					|| contexts[i] instanceof IContext);
		}
		action.setHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent event) {
				if (contexts != null && contexts.length > 0
						&& getHelpUI() != null) {
					IContext context = null;
					if (contexts[0] instanceof String) {
						context = HelpSystem.getContext((String) contexts[0]);
					} else if (contexts[0] instanceof IContext) {
						context = (IContext) contexts[0];
					}
					if (context != null) {
						Point point = computePopUpLocation(event.widget
								.getDisplay());
						displayContext(context, point.x, point.y);
					}
				}
			}
		});
	}

	@Deprecated
	public void setHelp(IAction action, final IContextComputer computer) {
		action.setHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent event) {
				Object[] helpContexts = computer.computeContexts(event);
				if (helpContexts != null && helpContexts.length > 0
						&& getHelpUI() != null) {
					IContext context = null;
					if (helpContexts[0] instanceof String) {
						context = HelpSystem
								.getContext((String) helpContexts[0]);
					} else if (helpContexts[0] instanceof IContext) {
						context = (IContext) helpContexts[0];
					}
					if (context != null) {
						Point point = computePopUpLocation(event.widget
								.getDisplay());
						displayContext(context, point.x, point.y);
					}
				}
			}
		});
	}

	@Deprecated
	public void setHelp(Control control, Object[] contexts) {
		for (int i = 0; i < contexts.length; i++) {
			Assert.isTrue(contexts[i] instanceof String
					|| contexts[i] instanceof IContext);
		}

		control.setData(HELP_KEY, contexts);
		control.removeHelpListener(getHelpListener());
		control.addHelpListener(getHelpListener());
	}

	@Deprecated
	public void setHelp(Control control, IContextComputer computer) {
		control.setData(HELP_KEY, computer);
		control.removeHelpListener(getHelpListener());
		control.addHelpListener(getHelpListener());
	}

	@Deprecated
	public void setHelp(Menu menu, Object[] contexts) {
		for (int i = 0; i < contexts.length; i++) {
			Assert.isTrue(contexts[i] instanceof String
					|| contexts[i] instanceof IContext);
		}
		menu.setData(HELP_KEY, contexts);
		menu.removeHelpListener(getHelpListener());
		menu.addHelpListener(getHelpListener());
	}

	@Deprecated
	public void setHelp(Menu menu, IContextComputer computer) {
		menu.setData(HELP_KEY, computer);
		menu.removeHelpListener(getHelpListener());
		menu.addHelpListener(getHelpListener());
	}

	@Deprecated
	public void setHelp(MenuItem item, Object[] contexts) {
		for (int i = 0; i < contexts.length; i++) {
			Assert.isTrue(contexts[i] instanceof String
					|| contexts[i] instanceof IContext);
		}
		item.setData(HELP_KEY, contexts);
		item.removeHelpListener(getHelpListener());
		item.addHelpListener(getHelpListener());
	}

	@Deprecated
	public void setHelp(MenuItem item, IContextComputer computer) {
		item.setData(HELP_KEY, computer);
		item.removeHelpListener(getHelpListener());
		item.addHelpListener(getHelpListener());
	}
	
	public HelpListener createHelpListener(ICommand command) {
		final String contextId = ""; //$NON-NLS-1$
		return new HelpListener() {
			@Override
			public void helpRequested(HelpEvent event) {
				if (getHelpUI() != null) {
					IContext context = HelpSystem.getContext(contextId);
					if (context != null) {
						Point point = computePopUpLocation(event.widget
								.getDisplay());
						displayContext(context, point.x, point.y);
					}
				}
			}
		};
	}

	@Override
	public void displayHelp() {
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null) {
			helpUI.displayHelp();
		}
	}

	@Override
	public void displaySearch() {
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null) {
			helpUI.displaySearch();
		}
	}

	@Override
	public void displayDynamicHelp() {
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null) {
			helpUI.displayDynamicHelp();
		}
	}

	@Override
	public void search(String expression) {
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null) {
			helpUI.search(expression);
		}
	}

	@Override
	public URL resolve(String href, boolean documentOnly) {
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null) {
			return helpUI.resolve(href, documentOnly);
		}
		return null;
	}

	@Override
	public void displayContext(IContext context, int x, int y) {
		if (context == null) {
			throw new IllegalArgumentException();
		}
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null) {
			helpUI.displayContext(context, x, y);
		}
	}

	@Override
	public void displayHelpResource(String href) {
		if (href == null) {
			throw new IllegalArgumentException();
		}
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null) {
			helpUI.displayHelpResource(href);
		}
	}

	@Override
	public void displayHelp(String contextId) {
		IContext context = HelpSystem.getContext(contextId);
		if (context != null) {
			Point point = computePopUpLocation(Display.getCurrent());
			displayContext(context, point.x, point.y);
		}
	}

	@Override
	public void displayHelp(IContext context) {
		Point point = computePopUpLocation(Display.getCurrent());
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null) {
			helpUI.displayContext(context, point.x, point.y);
		}
	}

	@Override
	public boolean isContextHelpDisplayed() {
		if (!isInitialized) {
			return false;
		}
		AbstractHelpUI helpUI = getHelpUI();
		return helpUI != null && helpUI.isContextHelpDisplayed();
	}

	@Override
	public void setHelp(final IAction action, final String contextId) {
		if (WorkbenchPlugin.DEBUG)
			setHelpTrace(contextId);
		action.setHelpListener(new HelpListener() {
			@Override
			public void helpRequested(HelpEvent event) {
				if (getHelpUI() != null) {
					IContext context = HelpSystem.getContext(contextId);
					if (context != null) {
						Point point = computePopUpLocation(event.widget
								.getDisplay());
						String title = LegacyActionTools.removeMnemonics(action.getText());
						displayContext(new ContextWithTitle(context, title), point.x, point.y);
					}
				}
			}
		});
	}

	@Override
	public void setHelp(Control control, String contextId) {
		if (WorkbenchPlugin.DEBUG)
			setHelpTrace(contextId);
		control.setData(HELP_KEY, contextId);
		control.removeHelpListener(getHelpListener());
		control.addHelpListener(getHelpListener());
	}

	@Override
	public void setHelp(Menu menu, String contextId) {
		if (WorkbenchPlugin.DEBUG)
			setHelpTrace(contextId);
		menu.setData(HELP_KEY, contextId);
		menu.removeHelpListener(getHelpListener());
		menu.addHelpListener(getHelpListener());
	}

	@Override
	public void setHelp(MenuItem item, String contextId) {

		if (WorkbenchPlugin.DEBUG)
			setHelpTrace(contextId);

		item.setData(HELP_KEY, contextId);
		item.removeHelpListener(getHelpListener());
		item.addHelpListener(getHelpListener());
	}

	private void setHelpTrace(String contextId) {
		RuntimeException e = new RuntimeException();
		StackTraceElement[] stackTrace = e.getStackTrace();
		StackTraceElement currentElement = null;
		for (int s = 0; s < stackTrace.length; s++) {
			if (stackTrace[s].getMethodName().equals("setHelp") && s + 1 < stackTrace.length) //$NON-NLS-1$
			{
				currentElement = stackTrace[s + 1];
				break;
			}
		}

		if (registeredIDTable == null)
			registeredIDTable = new Hashtable();

		if (!registeredIDTable.containsKey(contextId))
			registeredIDTable.put(contextId, currentElement);
		else if (!registeredIDTable.get(contextId).equals(currentElement)) {
			StackTraceElement initialElement = (StackTraceElement) registeredIDTable
					.get(contextId);
			String error = "UI Duplicate Context ID found: '" + contextId + "'\n" + //$NON-NLS-1$ //$NON-NLS-2$
					" 1 at " + initialElement + '\n' + //$NON-NLS-1$
					" 2 at " + currentElement; //$NON-NLS-1$

			System.out.println(error);
		}
	}

	@Override
	public boolean hasHelpUI() {
		return getHelpUI() != null;
	}
}
