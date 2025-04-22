package org.eclipse.ui;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IOpenListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;


public abstract class OpenAndLinkWithEditorHelper {

	private StructuredViewer viewer;

	private boolean isLinkingEnabled;

	private ISelection lastOpenSelection;

	private InternalListener listener;


	private final class InternalListener implements IOpenListener, ISelectionChangedListener, IDoubleClickListener {
		@Override
		public final void open(OpenEvent event) {
			lastOpenSelection = event.getSelection();
			OpenAndLinkWithEditorHelper.this.open(lastOpenSelection, OpenStrategy.activateOnOpen());
		}

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			final ISelection selection = event.getSelection();
			if (isLinkingEnabled && !selection.equals(lastOpenSelection) && viewer.getControl().isFocusControl())
				linkToEditor(selection);
			lastOpenSelection = null;
		}

		@Override
		public void doubleClick(DoubleClickEvent event) {
			if (!OpenStrategy.activateOnOpen())
				activate(event.getSelection());
		}

	}


	public OpenAndLinkWithEditorHelper(StructuredViewer viewer) {
		Assert.isLegal(viewer != null);
		this.viewer = viewer;
		listener = new InternalListener();
		viewer.addPostSelectionChangedListener(listener);
		viewer.addOpenListener(listener);
		viewer.addDoubleClickListener(listener);
	}

	public void setLinkWithEditor(boolean enabled) {
		isLinkingEnabled = enabled;
	}

	public void dispose() {
		viewer.removePostSelectionChangedListener(listener);
		viewer.removeOpenListener(listener);
		viewer.removeDoubleClickListener(listener);
		listener = null;
	}

	protected abstract void activate(ISelection selection);

	protected abstract void open(ISelection selection, boolean activate);

	protected void linkToEditor(ISelection selection) {
	}

}
