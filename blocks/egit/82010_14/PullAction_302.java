package org.eclipse.egit.ui.internal.synchronize.action;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.egit.core.AdapterUtils;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.egit.ui.internal.synchronize.model.GitModelObject;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.SelectionListenerAction;
import org.eclipse.ui.ide.IDE;

public class OpenWorkingFileAction extends SelectionListenerAction {

	private IWorkbenchPage workbenchPage;

	public OpenWorkingFileAction(IWorkbenchPage page) {
		super(null);
		setText(UIText.OpenWorkingFileAction_text);
		setToolTipText(UIText.OpenWorkingFileAction_tooltip);
		this.workbenchPage = page;
	}

	protected IWorkbenchPage getWorkbenchPage() {
		return workbenchPage;
	}

	protected void openWorkspaceFile(IFile file) {
		try {
			boolean activate = OpenStrategy.activateOnOpen();
			IDE.openEditor(getWorkbenchPage(), file, activate);
		} catch (PartInitException e) {
			ErrorDialog.openError(getWorkbenchPage().getWorkbenchWindow()
					.getShell(),
					UIText.OpenWorkingFileAction_openWorkingFileShellTitle, e
							.getMessage(), e.getStatus());
		}
	}

	protected void openExternalFile(File file) {
		try {
			boolean activate = OpenStrategy.activateOnOpen();
			IEditorDescriptor desc = IDE.getEditorDescriptor(file.getName());
			IDE.openEditor(getWorkbenchPage(), file.toURI(), desc.getId(),
					activate);
		} catch (PartInitException e) {
			ErrorDialog.openError(getWorkbenchPage().getWorkbenchWindow()
					.getShell(),
					UIText.OpenWorkingFileAction_openWorkingFileShellTitle, e
							.getMessage(), e.getStatus());
		}
	}

	@Override
	public void run() {
		IStructuredSelection selection = getStructuredSelection();

		IResource resource = getExistingResource(selection);

		if (resource instanceof IFile)
			openWorkspaceFile((IFile) resource);
		else {
			Object element = selection.getFirstElement();

			if (element instanceof GitModelObject) {
				IPath location = ((GitModelObject) element).getLocation();

				if (location != null && location.toFile().exists())
					openExternalFile(location.toFile());
			}
		}
	}

	@Nullable
	private IResource getExistingResource(IStructuredSelection selection) {
		Object element = selection.getFirstElement();
		IResource resource = AdapterUtils.adapt(element, IResource.class);
		if (resource != null && resource.exists()) {
			return resource;
		}
		return null;
	}

	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		return super.updateSelection(selection)
				&& selectionIsOfType(IResource.FILE);
	}
}
