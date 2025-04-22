package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;
import org.eclipse.ui.WorkbenchException;

public class WWinPluginPulldown extends WWinPluginAction {

	private final IMenuCreator menuProxy;

	private class MenuProxy implements IMenuCreator {

		private class MenuLoader implements ISafeRunnable {

			private final Control control;

			private final IWorkbenchWindowPulldownDelegate delegate;

			private Menu menu = null;

			private final Menu parent;

			private MenuLoader(
					final IWorkbenchWindowPulldownDelegate2 delegate,
					final Menu parent) {
				this.delegate = delegate;
				this.parent = parent;
				this.control = null;
			}

			private MenuLoader(final IWorkbenchWindowPulldownDelegate delegate,
					final Control parent) {
				this.delegate = delegate;
				this.parent = null;
				this.control = parent;
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

		@Override
		public Menu getMenu(Control parent) {
			IWorkbenchWindowPulldownDelegate delegate = getPulldownDelegate();
			if (delegate != null) {
				final MenuLoader menuLoader = new MenuLoader(delegate, parent);
				SafeRunner.run(menuLoader);
				return menuLoader.getMenu();
			}

			return null;
		}

		@Override
		public Menu getMenu(Menu parent) {
			IWorkbenchWindowPulldownDelegate delegate = getPulldownDelegate();

			if (delegate instanceof IWorkbenchWindowPulldownDelegate2) {
				IWorkbenchWindowPulldownDelegate2 delegate2 = (IWorkbenchWindowPulldownDelegate2) delegate;
				final MenuLoader menuLoader = new MenuLoader(delegate2, parent);
				SafeRunner.run(menuLoader);
				return menuLoader.getMenu();
			}

			return null;
		}

		@Override
		public void dispose() {
		}
	}

	public WWinPluginPulldown(IConfigurationElement actionElement,
			IWorkbenchWindow window, String id, int style) {
		super(actionElement, window, id, style);
		menuProxy = new MenuProxy();
		setMenuCreator(menuProxy);
	}

	@Override
	protected IActionDelegate validateDelegate(Object obj)
			throws WorkbenchException {
		if (obj instanceof IWorkbenchWindowPulldownDelegate) {
			return (IWorkbenchWindowPulldownDelegate) obj;
		}

		throw new WorkbenchException(
				"Action must implement IWorkbenchWindowPulldownDelegate"); //$NON-NLS-1$
	}

	protected IWorkbenchWindowPulldownDelegate getPulldownDelegate() {
		IActionDelegate delegate = getDelegate();
		if (delegate == null) {
			createDelegate();
			delegate = getDelegate();
		}
		return (IWorkbenchWindowPulldownDelegate) delegate;
	}
}
