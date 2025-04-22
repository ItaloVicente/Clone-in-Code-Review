/*******************************************************************************
 * Copyright (c) 2004, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Chris Austin (IBM) - Fix for bug 296042
 *******************************************************************************/
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

/**
 * This class represents a refactoring of the functionality previously contained
 * in <code>WorkbenchHelp</code>.
 *
 * @since 3.1
 */
public final class WorkbenchHelpSystem implements IWorkbenchHelpSystem {

	/**
	 * Key used for stashing help-related data on SWT widgets.
	 *
	 * @see org.eclipse.swt.widgets.Widget#getData(java.lang.String)
	 */

	/**
	 * Id of extension point where the help UI is contributed.
	 */
	private static final String HELP_SYSTEM_EXTENSION_ID = PlatformUI.PLUGIN_ID + '.' + IWorkbenchRegistryConstants.PL_HELPSUPPORT;

	/**
	 * Attribute id for class attribute of help UI extension point.
	 */

	/**
	 * Singleton.
	 */
	private static WorkbenchHelpSystem instance;

	/**
	 * The help listener.
	 */
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

			/*
			 * If can't find it, show the "context is missing" context.
			 */
			if (context == null) {
				context = HelpSystem.getContext(IWorkbenchHelpContextIds.MISSING);
			}

			if (context != null) {
				Point point = computePopUpLocation(event.widget.getDisplay());
				getInstance().displayContext(context, point.x, point.y);
			}
		}
	}

	/**
	 * Whether the help system has been initialized.
	 */
	private boolean isInitialized;

	/**
	 * Pluggable help UI, or <code>null</code> if none (or unknown).
	 */
	private AbstractHelpUI pluggableHelpUI = null;

	/**
	 * The id of the help extension that should be used. This is used only for
	 * debugging purposes.
	 */
	private String desiredHelpSystemId;

	/**
	 * Table for tracing registered context ids. This is used only for debugging
	 * purposes.
	 *
	 */
	private Hashtable registeredIDTable;

	/**
	 * Handles dynamic removal of the help system.
	 *
	 * @since 3.1
	 */
    private IExtensionChangeHandler handler = new IExtensionChangeHandler() {

    	@Override
		public void addExtension(IExtensionTracker tracker,IExtension extension) {
        }

        @Override
		public void removeExtension(IExtension source, Object[] objects) {
            for (Object object : objects) {
                if (object == pluggableHelpUI) {
                    isInitialized = false;
                    pluggableHelpUI = null;
                    helpCompatibilityWrapper = null;
                    PlatformUI.getWorkbench().getExtensionTracker()
							.unregisterHandler(handler);
                }
            }
        }
    };

	/**
	 * Compatibility implementation of old IHelp interface.
	 * WorkbenchHelp.getHelpSupport and IHelp were deprecated in 3.0.
	 */
	private class CompatibilityIHelpImplementation implements IHelp {

		/** @deprecated */
		@Deprecated
		@Override
		public void displayHelp() {
			AbstractHelpUI helpUI = getHelpUI();
			if (helpUI != null) {
				helpUI.displayHelp();
			}
		}

		/** @deprecated */
		@Deprecated
		@Override
		public void displayContext(IContext context, int x, int y) {
			AbstractHelpUI helpUI = getHelpUI();
			if (helpUI != null) {
				helpUI.displayContext(context, x, y);
			}
		}

		/** @deprecated */
		@Deprecated
		@Override
		public void displayContext(String contextId, int x, int y) {
			IContext context = HelpSystem.getContext(contextId);
			if (context != null) {
				displayContext(context, x, y);
			}
		}

		/** @deprecated */
		@Deprecated
		@Override
		public void displayHelpResource(String href) {
			AbstractHelpUI helpUI = getHelpUI();
			if (helpUI != null) {
				helpUI.displayHelpResource(href);
			}
		}

		/** @deprecated */
		@Deprecated
		@Override
		public void displayHelpResource(IHelpResource helpResource) {
			displayHelpResource(helpResource.getHref());
		}

		/** @deprecated */
		@Deprecated
		@Override
		public void displayHelp(String toc) {
			displayHelpResource(toc);
		}

		/** @deprecated */
		@Deprecated
		@Override
		public void displayHelp(String toc, String selectedTopic) {
			displayHelpResource(selectedTopic);
		}

		/** @deprecated */
		@Deprecated
		@Override
		public void displayHelp(String contextId, int x, int y) {
			displayContext(contextId, x, y);
		}

		/** @deprecated */
		@Deprecated
		@Override
		public void displayHelp(IContext context, int x, int y) {
			displayContext(context, x, y);
		}

		/** @deprecated */
		@Deprecated
		@Override
		public IContext getContext(String contextId) {
			return HelpSystem.getContext(contextId);
		}

		/** @deprecated */
		@Deprecated
		@Override
		public IToc[] getTocs() {
			return HelpSystem.getTocs();
		}

		/** @deprecated */
		@Deprecated
		@Override
		public boolean isContextHelpDisplayed() {
			return WorkbenchHelpSystem.this.isContextHelpDisplayed();
		}
	}

	/**
	 * A wrapper for action help context that passes the action
	 * text to be used as a title.
	 * @since 3.1
	 */
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

	/**
	 * Compatibility wrapper, or <code>null</code> if none. Do not access
	 * directly; see getHelpSupport().
	 */
	private IHelp helpCompatibilityWrapper = null;

	/**
	 * The listener to attach to various widgets.
	 */
	private static HelpListener helpListener;

	/**
	 * For debug purposes only.
	 *
	 * @return the desired help system id
	 */
	public String getDesiredHelpSystemId() {
		return desiredHelpSystemId;
	}

	/**
	 * For debug purposes only.
	 *
	 * @param desiredHelpSystemId the desired help system id
	 */
	public void setDesiredHelpSystemId(String desiredHelpSystemId) {
		this.desiredHelpSystemId = desiredHelpSystemId;
	}

	/**
	 * Singleton Constructor.
	 */
	private WorkbenchHelpSystem() {
	}

	/**
	 * Return the singleton instance of this class.
	 *
	 * @return the singleton instance
	 */
	public static WorkbenchHelpSystem getInstance() {
		if (instance == null) {
			instance = new WorkbenchHelpSystem();
		}

		return instance;
	}

	/**
	 * Disposed of the singleton of this class if it has been created.
	 */
	public static void disposeIfNecessary() {
		if (instance != null) {
			instance.dispose();
			instance = null;
		}
	}

	/**
	 * Dispose of any resources allocated by this instance.
	 */
	public void dispose() {
		pluggableHelpUI = null;
		helpCompatibilityWrapper = null;
		isInitialized = false;
		PlatformUI.getWorkbench().getExtensionTracker()
				.unregisterHandler(handler);
	}

	/**
	 * Returns the help UI for the platform, if available. This method will
	 * initialize the help UI if necessary.
	 *
	 * @return the help UI, or <code>null</code> if none
	 */
	private AbstractHelpUI getHelpUI() {
		if (!isInitialized) {
			isInitialized = initializePluggableHelpUI();
		}
		return pluggableHelpUI;
	}

	/**
	 * Initializes the pluggable help UI by getting an instance via the
	 * extension point.
	 */
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
				for (IExtension extension : extensions) {
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

	/**
	 * Determines the location for the help popup shell given the widget which
	 * orginated the request for help.
	 *
	 * @param display
	 *            the display where the help will appear
	 */
	private static Point computePopUpLocation(Display display) {
		Point point = display.getCursorLocation();
		return new Point(point.x + 15, point.y);
	}

	/**
	 * Returns the help listener which activates the help support system.
	 *
	 * @return the help listener
	 */
	private HelpListener getHelpListener() {
		if (helpListener == null) {
			helpListener = new WorkbenchHelpListener();
		}
		return helpListener;
	}

	/**
	 * Returns the help support system for the platform, if available.
	 *
	 * @return the help support system, or <code>null</code> if none
	 * @deprecated Use the static methods on this class and on
	 *             {@link org.eclipse.help.HelpSystem HelpSystem}instead of the
	 *             IHelp methods on the object returned by this method.
	 */
	@Deprecated
	public IHelp getHelpSupport() {
		AbstractHelpUI helpUI = getHelpUI();
		if (helpUI != null && helpCompatibilityWrapper == null) {
			helpCompatibilityWrapper = new CompatibilityIHelpImplementation();
		}
		return helpCompatibilityWrapper;

	}

	/**
	 * Sets the given help contexts on the given action.
	 * <p>
	 * Use this method when the list of help contexts is known in advance. Help
	 * contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param action
	 *            the action on which to register the computer
	 * @param contexts
	 *            the contexts to use when F1 help is invoked; a mixed-type
	 *            array of context ids (type <code>String</code>) and/or help
	 *            contexts (type <code>IContext</code>)
	 * @deprecated use setHelp with a single context id parameter
	 */
	@Deprecated
	public void setHelp(IAction action, final Object[] contexts) {
		for (Object context : contexts) {
			Assert.isTrue(context instanceof String
					|| context instanceof IContext);
		}
		action.setHelpListener(event -> {
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
		});
	}

	/**
	 * Sets the given help context computer on the given action.
	 * <p>
	 * Use this method when the help contexts cannot be computed in advance.
	 * Help contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param action
	 *            the action on which to register the computer
	 * @param computer
	 *            the computer to determine the help contexts for the control
	 *            when F1 help is invoked
	 * @deprecated context computers are no longer supported, clients should
	 *             implement their own help listener
	 */
	@Deprecated
	public void setHelp(IAction action, final IContextComputer computer) {
		action.setHelpListener(event -> {
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
		});
	}

	/**
	 * Sets the given help contexts on the given control.
	 * <p>
	 * Use this method when the list of help contexts is known in advance. Help
	 * contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param control
	 *            the control on which to register the contexts
	 * @param contexts
	 *            the contexts to use when F1 help is invoked; a mixed-type
	 *            array of context ids (type <code>String</code>) and/or help
	 *            contexts (type <code>IContext</code>)
	 * @deprecated use setHelp with single context id parameter
	 */
	@Deprecated
	public void setHelp(Control control, Object[] contexts) {
		for (Object context : contexts) {
			Assert.isTrue(context instanceof String
					|| context instanceof IContext);
		}

		control.setData(HELP_KEY, contexts);
		control.removeHelpListener(getHelpListener());
		control.addHelpListener(getHelpListener());
	}

	/**
	 * Sets the given help context computer on the given control.
	 * <p>
	 * Use this method when the help contexts cannot be computed in advance.
	 * Help contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param control
	 *            the control on which to register the computer
	 * @param computer
	 *            the computer to determine the help contexts for the control
	 *            when F1 help is invoked
	 * @deprecated context computers are no longer supported, clients should
	 *             implement their own help listener
	 */
	@Deprecated
	public void setHelp(Control control, IContextComputer computer) {
		control.setData(HELP_KEY, computer);
		control.removeHelpListener(getHelpListener());
		control.addHelpListener(getHelpListener());
	}

	/**
	 * Sets the given help contexts on the given menu.
	 * <p>
	 * Use this method when the list of help contexts is known in advance. Help
	 * contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param menu
	 *            the menu on which to register the context
	 * @param contexts
	 *            the contexts to use when F1 help is invoked; a mixed-type
	 *            array of context ids (type <code>String</code>) and/or help
	 *            contexts (type <code>IContext</code>)
	 * @deprecated use setHelp with single context id parameter
	 */
	@Deprecated
	public void setHelp(Menu menu, Object[] contexts) {
		for (Object context : contexts) {
			Assert.isTrue(context instanceof String
					|| context instanceof IContext);
		}
		menu.setData(HELP_KEY, contexts);
		menu.removeHelpListener(getHelpListener());
		menu.addHelpListener(getHelpListener());
	}

	/**
	 * Sets the given help context computer on the given menu.
	 * <p>
	 * Use this method when the help contexts cannot be computed in advance.
	 * Help contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param menu
	 *            the menu on which to register the computer
	 * @param computer
	 *            the computer to determine the help contexts for the control
	 *            when F1 help is invoked
	 * @deprecated context computers are no longer supported, clients should
	 *             implement their own help listener
	 */
	@Deprecated
	public void setHelp(Menu menu, IContextComputer computer) {
		menu.setData(HELP_KEY, computer);
		menu.removeHelpListener(getHelpListener());
		menu.addHelpListener(getHelpListener());
	}

	/**
	 * Sets the given help contexts on the given menu item.
	 * <p>
	 * Use this method when the list of help contexts is known in advance. Help
	 * contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param item
	 *            the menu item on which to register the context
	 * @param contexts
	 *            the contexts to use when F1 help is invoked; a mixed-type
	 *            array of context ids (type <code>String</code>) and/or help
	 *            contexts (type <code>IContext</code>)
	 * @deprecated use setHelp with single context id parameter
	 */
	@Deprecated
	public void setHelp(MenuItem item, Object[] contexts) {
		for (Object context : contexts) {
			Assert.isTrue(context instanceof String
					|| context instanceof IContext);
		}
		item.setData(HELP_KEY, contexts);
		item.removeHelpListener(getHelpListener());
		item.addHelpListener(getHelpListener());
	}

	/**
	 * Sets the given help context computer on the given menu item.
	 * <p>
	 * Use this method when the help contexts cannot be computed in advance.
	 * Help contexts can either supplied as a static list, or calculated with a
	 * context computer (but not both).
	 * </p>
	 *
	 * @param item
	 *            the menu item on which to register the computer
	 * @param computer
	 *            the computer to determine the help contexts for the control
	 *            when F1 help is invoked
	 * @deprecated context computers are no longer supported, clients should
	 *             implement their own help listener
	 */
	@Deprecated
	public void setHelp(MenuItem item, IContextComputer computer) {
		item.setData(HELP_KEY, computer);
		item.removeHelpListener(getHelpListener());
		item.addHelpListener(getHelpListener());
	}

    /**
     * Creates a new help listener for the given command. This retrieves the
     * help context ID from the command, and creates an appropriate listener
     * based on this.
     *
     * @param command
     *            The command for which the listener should be created; must
     *            not be <code>null</code>.
     * @return A help listener; never <code>null</code>.
     */
	public HelpListener createHelpListener(ICommand command) {
		return event -> {
			if (getHelpUI() != null) {
				IContext context = HelpSystem.getContext(contextId);
				if (context != null) {
					Point point = computePopUpLocation(event.widget
							.getDisplay());
					displayContext(context, point.x, point.y);
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
		action.setHelpListener(event -> {
			if (getHelpUI() != null) {
				IContext context = HelpSystem.getContext(contextId);
				if (context != null) {
					Point point = computePopUpLocation(event.widget
							.getDisplay());
					String title = LegacyActionTools.removeMnemonics(action.getText());
					displayContext(new ContextWithTitle(context, title), point.x, point.y);
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

	/*
	 * Traces all calls to the setHelp method in an attempt to find and report
	 * duplicated context IDs.
	 */
	private void setHelpTrace(String contextId) {
		RuntimeException e = new RuntimeException();
		StackTraceElement[] stackTrace = e.getStackTrace();
		StackTraceElement currentElement = null;
		for (int s = 0; s < stackTrace.length; s++) {
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

			System.out.println(error);
		}
	}

	@Override
	public boolean hasHelpUI() {
		return getHelpUI() != null;
	}
}
