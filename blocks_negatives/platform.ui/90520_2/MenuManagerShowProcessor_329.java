/*******************************************************************************
 * Copyright (c) 2010, 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.workbench.renderers.swt;

import java.lang.reflect.Method;
import javax.inject.Inject;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.log.Logger;
import org.eclipse.e4.ui.internal.workbench.swt.AbstractPartRenderer;
import org.eclipse.e4.ui.internal.workbench.swt.Policy;
import org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator;
import org.eclipse.e4.ui.model.application.ui.menu.MMenu;
import org.eclipse.e4.ui.model.application.ui.menu.MPopupMenu;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Widget;

public class MenuManagerServiceFilter implements Listener {


	private static void trace(String msg, Widget menu, MMenu menuModel) {
		WorkbenchSWTActivator.trace(Policy.DEBUG_MENUS_FLAG, msg + ": " + menu + ": " //$NON-NLS-1$ //$NON-NLS-2$
				+ menuModel, null);
	}

	private static Method aboutToShow;

	public static Method getAboutToShow() {
		if (aboutToShow == null) {
			try {
				aboutToShow = MenuManager.class
				aboutToShow.setAccessible(true);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return aboutToShow;
	}

	@Inject
	private Logger logger;

	@Inject
	EModelService modelService;

	@Override
	public void handleEvent(final Event event) {
		SafeRunner.run(new ISafeRunnable() {
			@Override
			public void handleException(Throwable e) {
				if (e instanceof Error) {
					throw (Error) e;
				} else {
					if (logger != null) {
						logger.error(e);
					}
				}
			}

			@Override
			public void run() throws Exception {
				safeHandleEvent(event);
			}
		});
	}

	private void safeHandleEvent(Event event) {
		if (!(event.widget instanceof Menu)) {
			return;
		}
		final Menu menu = (Menu) event.widget;
		if (event.type == SWT.Dispose) {
			trace("handleMenu.Dispose", menu, null); //$NON-NLS-1$
			cleanUp(menu);
		}
		Object obj = menu.getData(AbstractPartRenderer.OWNING_ME);
		if (obj == null && menu.getParentItem() != null) {
			obj = menu.getParentItem().getData(AbstractPartRenderer.OWNING_ME);
		}
		if (obj instanceof MPopupMenu) {
			handleContextMenu(event, menu, (MPopupMenu) obj);
		} else if (obj instanceof MMenu) {
			handleMenu(event, menu, (MMenu) obj);
		}
	}

	private void handleMenu(final Event event, final Menu menu,
			final MMenu menuModel) {
		if ((menu.getStyle() & SWT.BAR) != 0) {
			return;
		}
		switch (event.type) {
		case SWT.Show:
			cleanUp(menu);
			showMenu(event, menu, menuModel);
			break;
		case SWT.Hide:
			break;
		}
	}

	public void showMenu(final Event event, final Menu menu,
			final MMenu menuModel) {
	}

	private void handleContextMenu(final Event event, final Menu menu,
			final MPopupMenu menuModel) {
		switch (event.type) {
		case SWT.Show:
			cleanUp(menu);
			showPopup(event, menu, menuModel);
			break;
		case SWT.Hide:
			hidePopup(event, menu, menuModel);
			break;
		}
	}

	public void hidePopup(Event event, Menu menu, MPopupMenu menuModel) {
		final IEclipseContext popupContext = menuModel.getContext();
		final IEclipseContext originalChild = (IEclipseContext) popupContext
				.get(TMP_ORIGINAL_CONTEXT);
		popupContext.remove(TMP_ORIGINAL_CONTEXT);
		if (!menu.isDisposed()) {
			menu.getDisplay().asyncExec(() -> {
				if (originalChild == null) {
					popupContext.deactivate();
				} else {
					originalChild.activate();
				}
			});
		}
	}

	public void showPopup(final Event event, final Menu menu,
			final MPopupMenu menuModel) {
		final IEclipseContext popupContext = menuModel.getContext();
		final IEclipseContext parentContext = popupContext.getParent();
		final IEclipseContext originalChild = parentContext.getActiveChild();
		popupContext.activate();
		popupContext.set(TMP_ORIGINAL_CONTEXT, originalChild);

	}

	public void cleanUp(final Menu menu) {
	}

	public void dispose() {
	}
}
