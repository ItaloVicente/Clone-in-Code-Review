package org.eclipse.egit.ui.internal.commit;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public class FocusTrackingFormPage extends FormPage {

	private final Set<Control> trackedControls = new HashSet<>();

	private final DisposeListener disposeListener = event -> trackedControls
			.remove(event.widget);

	private final FocusListener focusTracker = new FocusAdapter() {

		@Override
		public void focusLost(FocusEvent e) {
			if (e.widget instanceof Control) {
				lastFocusControl = (Control) e.widget;
			}
		}
	};

	private Control lastFocusControl;

	public FocusTrackingFormPage(String id, String title) {
		super(id, title);
	}

	public FocusTrackingFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	protected void addToFocusTracking(@NonNull Control control) {
		if (trackedControls.add(control)) {
			control.addDisposeListener(disposeListener);
			control.addFocusListener(focusTracker);
		}
	}

	protected void removeFromFocusTracking(Control control) {
		if (trackedControls.remove(control) && !control.isDisposed()) {
			control.removeDisposeListener(disposeListener);
			control.removeFocusListener(focusTracker);
		}
	}

	@Override
	public void setFocus() {
		if (lastFocusControl != null) {
			if (lastFocusControl.isDisposed()) {
				trackedControls.remove(lastFocusControl);
				lastFocusControl = null;
			} else if (lastFocusControl.forceFocus()) {
				return;
			}
		}
		getManagedForm().getForm().setFocus();
	}

	@Override
	public void dispose() {
		for (Control tracked : trackedControls) {
			if (!tracked.isDisposed()) {
				tracked.removeDisposeListener(disposeListener);
				tracked.removeFocusListener(focusTracker);
			}
		}
		trackedControls.clear();
		lastFocusControl = null;
		super.dispose();
	}
}
