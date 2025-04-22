package org.eclipse.egit.ui.internal.history;

import org.eclipse.core.resources.IResource;
import org.eclipse.egit.ui.internal.selection.SelectionUtils;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.team.internal.ui.history.GenericHistoryView;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.ide.ResourceUtil;

@SuppressWarnings("restriction")
public class GitHistorySelectionTracker
		implements ISelectionListener, IPartListener, IPartListener2,
		IPageChangedListener {

	protected IStructuredSelection selection;

	@Override
	public void partActivated(IWorkbenchPartReference partRef) {
		partActivated(partRef.getPart(false));
	}

	@Override
	public void partBroughtToTop(IWorkbenchPartReference partRef) {
		partBroughtToTop(partRef.getPart(false));
	}

	@Override
	public void partClosed(IWorkbenchPartReference partRef) {
		partClosed(partRef.getPart(false));
	}

	@Override
	public void partDeactivated(IWorkbenchPartReference partRef) {
		partDeactivated(partRef.getPart(false));
	}

	@Override
	public void partOpened(IWorkbenchPartReference partRef) {
		partOpened(partRef.getPart(false));
	}

	@Override
	public void partHidden(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partVisible(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partInputChanged(IWorkbenchPartReference partRef) {
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof IEditorPart) {
			editorActivated((IEditorPart) part);
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
	}

	@Override
	public void pageChanged(PageChangedEvent event) {
		if (event.getSelectedPage() instanceof IEditorPart) {
			editorActivated((IEditorPart) event.getSelectedPage());
		}
	}

	@SuppressWarnings({ "hiding" })
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection == null || part instanceof GenericHistoryView) {
			return;
		}
		IStructuredSelection structuredSelection = SelectionUtils
				.getStructuredSelection(selection);
		if (structuredSelection.isEmpty()) {
			return;
		}
		setSelection(structuredSelection);
	}

	protected void editorActivated(IEditorPart editor) {
		IEditorInput editorInput = editor.getEditorInput();
		IResource resource = ResourceUtil.getResource(editorInput);
		if (resource != null) {
			setSelection(new StructuredSelection(resource));
		}
	}

	public void attach(IWorkbenchPage page) {
		if (page != null) {
			page.addPartListener((IPartListener2) this);
			page.addSelectionListener(this);
		}
	}

	public void detach(IWorkbenchPage page) {
		if (page != null) {
			page.removePartListener((IPartListener2) this);
			page.removeSelectionListener(this);
		}
	}

	private void setSelection(IStructuredSelection selection) {
		this.selection = selection;
	}

	public IStructuredSelection getSelection() {
		return selection;
	}

	public void clearSelection() {
		setSelection(null);
	}
}
