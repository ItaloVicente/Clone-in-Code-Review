
package org.eclipse.ui.internal.menus;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.menus.IWidget;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.services.IServiceLocator;

final class PulldownDelegateWidgetProxy implements IWidget {

	private static final class MenuLoader implements ISafeRunnable {

		private final Control control;

		private final IWorkbenchWindowPulldownDelegate delegate;

		private Menu menu = null;

		private final Menu parent;

		private MenuLoader(final IWorkbenchWindowPulldownDelegate delegate,
				final Control parent) {
			this.delegate = delegate;
			this.parent = null;
			this.control = parent;
		}

		private MenuLoader(final IWorkbenchWindowPulldownDelegate2 delegate,
				final Menu parent) {
			this.delegate = delegate;
			this.parent = parent;
			this.control = null;
		}

		private Menu getMenu() {
			return menu;
		}

		@Override
		public void handleException(Throwable exception) {
		}

		@Override
		public void run() throws Exception {
			if (parent == null) {
				menu = delegate.getMenu(control);
			} else {
				menu = ((IWorkbenchWindowPulldownDelegate2) delegate)
						.getMenu(parent);
			}
		}
	}

	private final ParameterizedCommand command;

	private IConfigurationElement configurationElement;

	private IWorkbenchWindowPulldownDelegate delegate = null;

	private final String delegateAttributeName;

	private final DisposeListener disposeListener = new DisposeListener() {
		@Override
		public void widgetDisposed(DisposeEvent e) {
			if (e.widget == widget) {
				dispose();
				widget = null;

			}
		}
	};

	private final IServiceLocator locator;

	private final Listener selectionListener = new Listener() {
		@Override
		public final void handleEvent(final Event event) {
			final Widget item = event.widget;
			if (item == null) {
				return;
			}

			final int style = item.getStyle();
			if (((style & SWT.DROP_DOWN) != 0) && (event.detail == SWT.ARROW)
					&& (item instanceof ToolItem)) {
				final ToolItem toolItem = (ToolItem) item;
				final ToolBar toolBar = toolItem.getParent();
				if (loadDelegate()
						&& (delegate instanceof IWorkbenchWindowPulldownDelegate2)) {
					final IWorkbenchWindowPulldownDelegate2 delegate2 = (IWorkbenchWindowPulldownDelegate2) delegate;
					final MenuLoader loader = new MenuLoader(delegate2, toolBar);
					SafeRunner.run(loader);
					final Menu subMenu = loader.getMenu();
					if (subMenu != null) {
						final Rectangle bounds = toolItem.getBounds();
						final Point location = toolBar.toDisplay(new Point(
								bounds.x, bounds.y + bounds.height));
						subMenu.setLocation(location);
						subMenu.setVisible(true);
						return; // we don't fire the command
					}
				}
			}

			final IHandlerService service = locator
					.getService(IHandlerService.class);
			try {
				service.executeCommand(command, event);
			} catch (final CommandException e) {
			}
		}

	};

	private Widget widget = null;

	public PulldownDelegateWidgetProxy(
			final IConfigurationElement configurationElement,
			final String delegateAttributeName,
			final ParameterizedCommand command, final IServiceLocator locator) {
		if (configurationElement == null) {
			throw new NullPointerException(
					"The configuration element backing a handler proxy cannot be null"); //$NON-NLS-1$
		}

		if (delegateAttributeName == null) {
			throw new NullPointerException(
					"The attribute containing the handler class must be known"); //$NON-NLS-1$
		}

		if (command == null) {
			throw new NullPointerException("The command cannot be null"); //$NON-NLS-1$
		}

		this.configurationElement = configurationElement;
		this.delegateAttributeName = delegateAttributeName;
		this.command = command;
		this.locator = locator;
	}

	@Override
	public final void dispose() {
		if (delegate != null) {
			delegate.dispose();
		}
	}

	@Override
	public final void fill(final Composite parent) {
	}

	@Override
	public final void fill(CoolBar parent, final int index) {
	}

	@Override
	public final void fill(final Menu parent, final int index) {
		if ((widget != null) || (parent == null)) {
			return;
		}

		final MenuItem menuItem;
		if (index >= 0) {
			menuItem = new MenuItem(parent, SWT.CASCADE, index);
		} else {
			menuItem = new MenuItem(parent, SWT.CASCADE);
		}
		menuItem.setData(this);
		widget = menuItem;

		if (loadDelegate()
				&& (delegate instanceof IWorkbenchWindowPulldownDelegate2)) {
			final IWorkbenchWindowPulldownDelegate2 delegate2 = (IWorkbenchWindowPulldownDelegate2) delegate;
			final MenuLoader loader = new MenuLoader(delegate2, parent);
			SafeRunner.run(loader);
			final Menu subMenu = loader.getMenu();
			if (subMenu != null) {
				menuItem.setMenu(subMenu);
			}
		}

		menuItem.addDisposeListener(disposeListener);
		menuItem.addListener(SWT.Selection, selectionListener);


	}

	@Override
	public final void fill(final ToolBar parent, final int index) {
		if ((widget != null) && (parent == null)) {
			return;
		}

		final ToolItem toolItem;
		if (index >= 0) {
			toolItem = new ToolItem(parent, SWT.DROP_DOWN, index);
		} else {
			toolItem = new ToolItem(parent, SWT.DROP_DOWN);
		}
		toolItem.setData(this);
		widget = toolItem;

		toolItem.addDisposeListener(disposeListener);
		toolItem.addListener(SWT.Selection, selectionListener);


	}

	private final boolean loadDelegate() {
		if (delegate == null) {
			try {
				delegate = (IWorkbenchWindowPulldownDelegate) configurationElement
						.createExecutableExtension(delegateAttributeName);
				configurationElement = null;
				return true;

			} catch (final ClassCastException e) {
				final String message = "The proxied delegate was the wrong class"; //$NON-NLS-1$
				final IStatus status = new Status(IStatus.ERROR,
						WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);
				return false;

			} catch (final CoreException e) {
				final String message = "The proxied delegate for '" + configurationElement.getAttribute(delegateAttributeName) //$NON-NLS-1$
						+ "' could not be loaded"; //$NON-NLS-1$
				IStatus status = new Status(IStatus.ERROR,
						WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);
				return false;
			}
		}

		return true;
	}

	@Override
	public final String toString() {
		if (delegate == null) {
			return configurationElement.getAttribute(delegateAttributeName);
		}

		return delegate.toString();
	}
}
