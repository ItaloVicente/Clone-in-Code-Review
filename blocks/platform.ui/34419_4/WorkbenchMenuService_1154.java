
package org.eclipse.ui.internal.menus;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.menus.AbstractWorkbenchTrimWidget;
import org.eclipse.ui.menus.IWorkbenchWidget;

final class WidgetProxy implements IWorkbenchWidget {

	private boolean firstLoad = true;
	
	private IConfigurationElement configurationElement;

	private IWorkbenchWidget widget = null;

	private final String widgetAttributeName;

	public WidgetProxy(final IConfigurationElement configurationElement,
			final String widgetAttributeName) {
		if (configurationElement == null) {
			throw new NullPointerException(
					"The configuration element backing a widget proxy cannot be null"); //$NON-NLS-1$
		}

		if (widgetAttributeName == null) {
			throw new NullPointerException(
					"The attribute containing the widget class must be known"); //$NON-NLS-1$
		}

		this.configurationElement = configurationElement;
		this.widgetAttributeName = widgetAttributeName;
	}

	@Override
	public final void dispose() {
		if (loadWidget()) {
			widget.dispose();
		}
	}

	@Override
	public final void fill(final Composite parent) {
		if (loadWidget()) {
			widget.fill(parent);
		}
	}

	@Override
	public final void fill(final CoolBar parent, final int index) {
		if (loadWidget()) {
			widget.fill(parent, index);
		}
	}

	@Override
	public final void fill(final Menu parent, final int index) {
		if (loadWidget()) {
			widget.fill(parent, index);
		}
	}

	@Override
	public final void fill(final ToolBar parent, final int index) {
		if (loadWidget()) {
			widget.fill(parent, index);
		}
	}

	@Override
	public void init(IWorkbenchWindow workbenchWindow) {
		if (loadWidget()) {
			widget.init(workbenchWindow);
		}
	}

	public final void fill(Composite parent, int oldSide, int newSide) {
		if (loadWidget()) {
			if (isMoveableTrimWidget()) {
				((AbstractWorkbenchTrimWidget) widget).fill(parent, oldSide, newSide);
			} else {
				widget.fill(parent);
			}
		}
	}

	private final boolean loadWidget() {
		if (firstLoad) {
			try {
				widget = (IWorkbenchWidget) configurationElement
						.createExecutableExtension(widgetAttributeName);
				configurationElement = null;				
			} catch (final ClassCastException e) {
				final String message = "The proxied widget was the wrong class"; //$NON-NLS-1$
				final IStatus status = new Status(IStatus.ERROR,
						WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);

			} catch (final CoreException e) {
				final String message = "The proxied widget for '" + configurationElement.getAttribute(widgetAttributeName) //$NON-NLS-1$
						+ "' could not be loaded"; //$NON-NLS-1$
				IStatus status = new Status(IStatus.ERROR,
						WorkbenchPlugin.PI_WORKBENCH, 0, message, e);
				WorkbenchPlugin.log(message, status);
			}
		}

		firstLoad = false;
		
		return widget != null;
	}

	private final boolean isMoveableTrimWidget() {
		if (loadWidget()) {
			return widget instanceof AbstractWorkbenchTrimWidget;
		}
		
		return false;
	}
	
	@Override
	public final String toString() {
		if (widget == null) {
			return configurationElement.getAttribute(widgetAttributeName);
		}

		return widget.toString();
	}
}
