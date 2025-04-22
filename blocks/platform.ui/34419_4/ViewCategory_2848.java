package org.eclipse.ui.internal.registry;

import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class UIExtensionTracker extends ExtensionTracker {
    private Display display;


	public UIExtensionTracker(Display display) {
		this.display = display;
	}

	@Override
	protected void applyRemove(final IExtensionChangeHandler handler, final IExtension removedExtension, final Object[] objects) {
		if (display.isDisposed())
			return;

		display.syncExec(new Runnable() {

            @Override
			public void run() {
                try {
                    handler.removeExtension(removedExtension, objects);
                } catch (Exception e) {
                    WorkbenchPlugin.log(getClass(), "doRemove", e); //$NON-NLS-1$
                }
            }
        });
    }

    @Override
	protected void applyAdd(final IExtensionChangeHandler handler, final IExtension addedExtension) {
		if (display.isDisposed())
			return;

        display.syncExec(new Runnable() {
            @Override
			public void run() {
                try {
                    handler.addExtension(UIExtensionTracker.this, addedExtension);
                } catch (Exception e) {
                    WorkbenchPlugin.log(getClass(), "doAdd", e); //$NON-NLS-1$
                }
            }
        });
    }
}
