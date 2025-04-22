package org.eclipse.ui.navigator.resources;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CopyFilesAndFoldersOperation;
import org.eclipse.ui.actions.MoveFilesAndFoldersOperation;
import org.eclipse.ui.actions.ReadOnlyStateChecker;
import org.eclipse.ui.ide.dialogs.ImportTypeDialog;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.navigator.Policy;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorMessages;
import org.eclipse.ui.internal.navigator.resources.plugin.WorkbenchNavigatorPlugin;
import org.eclipse.ui.navigator.CommonDropAdapter;
import org.eclipse.ui.navigator.CommonDropAdapterAssistant;
import org.eclipse.ui.part.ResourceTransfer;

import org.eclipse.ltk.core.refactoring.CheckConditionsOperation;
import org.eclipse.ltk.core.refactoring.PerformRefactoringOperation;
import org.eclipse.ltk.core.refactoring.Refactoring;
import org.eclipse.ltk.core.refactoring.RefactoringContribution;
import org.eclipse.ltk.core.refactoring.RefactoringCore;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.resource.MoveResourcesDescriptor;
import org.eclipse.ltk.ui.refactoring.RefactoringUI;


public class ResourceDropAdapterAssistant extends CommonDropAdapterAssistant {

	private static final IResource[] NO_RESOURCES = new IResource[0];

	private RefactoringStatus refactoringStatus;
	private IStatus returnStatus;
	
	@Override
	public boolean isSupportedType(TransferData aTransferType) {
		return super.isSupportedType(aTransferType)
				|| FileTransfer.getInstance().isSupportedType(aTransferType);
	}

	@Override
	public IStatus validateDrop(Object target, int aDropOperation,
			TransferData transferType) {

		if (!(target instanceof IResource)) {
			return WorkbenchNavigatorPlugin
					.createStatus(
							IStatus.INFO,
							0,
							WorkbenchNavigatorMessages.DropAdapter_targetMustBeResource,
							null);
		}
		IResource resource = (IResource) target;
		if (!resource.isAccessible()) {
			return WorkbenchNavigatorPlugin
					.createErrorStatus(
							0,
							WorkbenchNavigatorMessages.DropAdapter_canNotDropIntoClosedProject,
							null);
		}
		IContainer destination = getActualTarget(resource);
		if (destination.getType() == IResource.ROOT) {
			return WorkbenchNavigatorPlugin
					.createErrorStatus(
							0,
							WorkbenchNavigatorMessages.DropAdapter_resourcesCanNotBeSiblings,
							null);
		}
		String message = null;
		if (LocalSelectionTransfer.getTransfer().isSupportedType(transferType)) {
			IResource[] selectedResources = getSelectedResources();
			
			boolean bProjectDrop = false;
			for (int iRes = 0; iRes < selectedResources.length; iRes++) {
				IResource res = selectedResources[iRes];
				if(res instanceof IProject) {
					bProjectDrop = true;
				}
			}
			if(bProjectDrop) {
				message = WorkbenchNavigatorMessages.DropAdapter_canNotDropProjectIntoProject;
			} else {
				if (selectedResources.length == 0) {
					message = WorkbenchNavigatorMessages.DropAdapter_dropOperationErrorOther;
				} else {
					CopyFilesAndFoldersOperation operation;
					if (aDropOperation == DND.DROP_COPY) {
						if (Policy.DEBUG_DND) {
							System.out
									.println("ResourceDropAdapterAssistant.validateDrop validating COPY."); //$NON-NLS-1$
						}
	
						operation = new CopyFilesAndFoldersOperation(getShell());
					} else {
						if (Policy.DEBUG_DND) {
							System.out
									.println("ResourceDropAdapterAssistant.validateDrop validating MOVE."); //$NON-NLS-1$
						}
						operation = new MoveFilesAndFoldersOperation(getShell());
					}
					if (operation.validateDestination(destination, selectedResources) != null) {
						operation.setVirtualFolders(true);
						message = operation.validateDestination(destination, selectedResources);
					}
				}
			}
		} // file import?
		else if (FileTransfer.getInstance().isSupportedType(transferType)) {
			String[] sourceNames = (String[]) FileTransfer.getInstance()
					.nativeToJava(transferType);
			if (sourceNames == null) {
				sourceNames = new String[0];
			}
			CopyFilesAndFoldersOperation copyOperation = new CopyFilesAndFoldersOperation(
					getShell());
			message = copyOperation.validateImportDestination(destination,
					sourceNames);
		}
		if (message != null) {
			return WorkbenchNavigatorPlugin.createErrorStatus(0, message, null);
		}
		return Status.OK_STATUS;
	}

	@Override
	public IStatus handleDrop(CommonDropAdapter aDropAdapter,
			DropTargetEvent aDropTargetEvent, Object aTarget) {

		if (Policy.DEBUG_DND) {
			System.out
					.println("ResourceDropAdapterAssistant.handleDrop (begin)"); //$NON-NLS-1$
		}

		if (aTarget == null || aDropTargetEvent.data == null) {
			return Status.CANCEL_STATUS;
		}
		IStatus status = null;
		IResource[] resources = null;
		TransferData currentTransfer = aDropAdapter.getCurrentTransfer();
		if (LocalSelectionTransfer.getTransfer().isSupportedType(
				currentTransfer)) {
			resources = getSelectedResources();
		} else if (ResourceTransfer.getInstance().isSupportedType(
				currentTransfer)) {
			resources = (IResource[]) aDropTargetEvent.data;
		}

		if (FileTransfer.getInstance().isSupportedType(currentTransfer)) {
			status = performFileDrop(aDropAdapter, aDropTargetEvent.data);
		} else if (resources != null && resources.length > 0) {
			if ((aDropAdapter.getCurrentOperation() == DND.DROP_COPY)
					|| (aDropAdapter.getCurrentOperation() == DND.DROP_LINK)) {
				if (Policy.DEBUG_DND) {
					System.out
							.println("ResourceDropAdapterAssistant.handleDrop executing COPY."); //$NON-NLS-1$
				}
				status = performResourceCopy(aDropAdapter, getShell(),
						resources);
			} else {
				if (Policy.DEBUG_DND) {
					System.out
							.println("ResourceDropAdapterAssistant.handleDrop executing MOVE."); //$NON-NLS-1$
				}

				status = performResourceMove(aDropAdapter, resources);
			}
		}
		openError(status);
		IContainer target = getActualTarget((IResource) aTarget);
		if (target != null && target.isAccessible()) {
			try {
				target.refreshLocal(IResource.DEPTH_ONE, null);
			} catch (CoreException e) {
			}
		}
		return status;
	}

	@Override
	public IStatus validatePluginTransferDrop(
			IStructuredSelection aDragSelection, Object aDropTarget) {
		if (!(aDropTarget instanceof IResource)) {
			return WorkbenchNavigatorPlugin
					.createStatus(
							IStatus.INFO,
							0,
							WorkbenchNavigatorMessages.DropAdapter_targetMustBeResource,
							null);
		}
		IResource resource = (IResource) aDropTarget;
		if (!resource.isAccessible()) {
			return WorkbenchNavigatorPlugin
					.createErrorStatus(
							0,
							WorkbenchNavigatorMessages.DropAdapter_canNotDropIntoClosedProject,
							null);
		}
		IContainer destination = getActualTarget(resource);
		if (destination.getType() == IResource.ROOT) {
			return WorkbenchNavigatorPlugin
					.createErrorStatus(
							0,
							WorkbenchNavigatorMessages.DropAdapter_resourcesCanNotBeSiblings,
							null);
		}

		IResource[] selectedResources = getSelectedResources(aDragSelection);

		String message = null;
		if (selectedResources.length == 0) {
			message = WorkbenchNavigatorMessages.DropAdapter_dropOperationErrorOther;
		} else {
			MoveFilesAndFoldersOperation operation;

			operation = new MoveFilesAndFoldersOperation(getShell());
			message = operation.validateDestination(destination,
					selectedResources);
		}
		if (message != null) {
			return WorkbenchNavigatorPlugin.createErrorStatus(0, message, null);
		}
		return Status.OK_STATUS;
	}
	
	@Override
	public IStatus handlePluginTransferDrop(IStructuredSelection aDragSelection, Object aDropTarget) {

		IContainer target = getActualTarget((IResource) aDropTarget);
		IResource[] resources = getSelectedResources(aDragSelection);
		
		MoveFilesAndFoldersOperation operation = new MoveFilesAndFoldersOperation(
				 getShell());
		operation.copyResources(resources, target);

		if (target != null && target.isAccessible()) {
			try {
				target.refreshLocal(IResource.DEPTH_ONE, null);
			} catch (CoreException e) {
			}
		}
		return Status.OK_STATUS;
	}

	private IContainer getActualTarget(IResource mouseTarget) {

		if (mouseTarget.getType() == IResource.FILE) {
			return mouseTarget.getParent();
		}
		return (IContainer) mouseTarget;
	}

	private IResource[] getSelectedResources() {

		ISelection selection = LocalSelectionTransfer.getTransfer()
				.getSelection();
		if (selection instanceof IStructuredSelection) {
			return getSelectedResources((IStructuredSelection)selection);
		}
		return NO_RESOURCES;
	}

	private IResource[] getSelectedResources(IStructuredSelection selection) {
		ArrayList<IResource> selectedResources = new ArrayList<IResource>();

		for (Iterator<?> i = selection.iterator(); i.hasNext();) {
			Object o = i.next();
			if (o instanceof IResource) {
				selectedResources.add((IResource)o);
			} else if (o instanceof IAdaptable) {
				IAdaptable a = (IAdaptable) o;
				IResource r = (IResource) a.getAdapter(IResource.class);
				if (r != null) {
					selectedResources.add(r);
				}
			}
		}
		return selectedResources.toArray(new IResource[selectedResources.size()]);
	}

	private IStatus performResourceCopy(CommonDropAdapter dropAdapter,
			Shell shell, IResource[] sources) {
		MultiStatus problems = new MultiStatus(PlatformUI.PLUGIN_ID, 1,
				WorkbenchNavigatorMessages.DropAdapter_problemsMoving, null);
		mergeStatus(problems, validateTarget(dropAdapter.getCurrentTarget(), dropAdapter.getCurrentTransfer(),
				dropAdapter.getCurrentOperation()));

		IContainer target = getActualTarget((IResource) dropAdapter.getCurrentTarget());

		boolean shouldLinkAutomatically = false;
		if (target.isVirtual()) {
			shouldLinkAutomatically = true;
			for (int i = 0; i < sources.length; i++) {
				if ((sources[i].getType() != IResource.FILE) && (sources[i].getLocation() != null)) {
					shouldLinkAutomatically = false;
					break;
				}
			}
		}

		CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(shell);
		if (shouldLinkAutomatically) {
			operation.setCreateLinks(true);
			operation.copyResources(sources, target);
		} else {
			boolean allSourceAreLinksOrVirtualFolders = true;
			for (int i = 0; i < sources.length; i++) {
				if (!sources[i].isVirtual() && !sources[i].isLinked()) {
					allSourceAreLinksOrVirtualFolders = false;
					break;
				}
			}
			if (!allSourceAreLinksOrVirtualFolders) {
				IPreferenceStore store= IDEWorkbenchPlugin.getDefault().getPreferenceStore();
				String dndPreference= store.getString(target.isVirtual() ? IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_VIRTUAL_FOLDER_MODE : IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE);

				if (dndPreference.equals(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_PROMPT)) {
					ImportTypeDialog dialog = new ImportTypeDialog(getShell(), dropAdapter.getCurrentOperation(), sources, target);
					dialog.setResource(target);
					if (dialog.open() == Window.OK) {
						if (dialog.getSelection() == ImportTypeDialog.IMPORT_VIRTUAL_FOLDERS_AND_LINKS)
							operation.setVirtualFolders(true);
						if (dialog.getSelection() == ImportTypeDialog.IMPORT_LINK)
							operation.setCreateLinks(true);
						if (dialog.getVariable() != null)
							operation.setRelativeVariable(dialog.getVariable());
						operation.copyResources(sources, target);
					} else
						return problems;
				}
				else
					operation.copyResources(sources, target);
			} else
				operation.copyResources(sources, target);
		}

		return problems;
	}

	private IStatus performResourceMove(CommonDropAdapter dropAdapter,
			IResource[] sources) {
		MultiStatus problems = new MultiStatus(PlatformUI.PLUGIN_ID, 1,
				WorkbenchNavigatorMessages.DropAdapter_problemsMoving, null);
		mergeStatus(problems, validateTarget(dropAdapter.getCurrentTarget(), dropAdapter.getCurrentTransfer(),
				dropAdapter.getCurrentOperation()));

		IContainer target = getActualTarget((IResource) dropAdapter.getCurrentTarget());

		boolean shouldLinkAutomatically = false;
		if (target.isVirtual()) {
			shouldLinkAutomatically = true;
			for (int i = 0; i < sources.length; i++) {
				if (sources[i].isVirtual() || sources[i].isLinked()) {
					shouldLinkAutomatically = false;
					break;
				}
			}
		}

		if (shouldLinkAutomatically) {
			CopyFilesAndFoldersOperation operation = new CopyFilesAndFoldersOperation(getShell());
			operation.setCreateLinks(true);
			operation.copyResources(sources, target);
		} else {
			ReadOnlyStateChecker checker = new ReadOnlyStateChecker(getShell(),
					WorkbenchNavigatorMessages.MoveResourceAction_title,
					WorkbenchNavigatorMessages.MoveResourceAction_checkMoveMessage);
			sources = checker.checkReadOnlyResources(sources);

			try {
				RefactoringContribution contribution = RefactoringCore
						.getRefactoringContribution(MoveResourcesDescriptor.ID);
				MoveResourcesDescriptor descriptor = (MoveResourcesDescriptor) contribution.createDescriptor();
				descriptor.setResourcesToMove(sources);
				descriptor.setDestination(target);
				refactoringStatus = new RefactoringStatus();
				final Refactoring refactoring = descriptor.createRefactoring(refactoringStatus);
				
				returnStatus = null;
				IRunnableWithProgress checkOp = new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						try {
						refactoringStatus = refactoring.checkAllConditions(monitor);
					} catch (CoreException ex) {
						returnStatus = WorkbenchNavigatorPlugin.createErrorStatus(0, ex.getLocalizedMessage(), ex);
					}}
				};
				
				if (returnStatus != null)
					return returnStatus;

				try {
					PlatformUI.getWorkbench().getProgressService().run(false, false, checkOp);
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				} catch (InvocationTargetException e) {
					return WorkbenchNavigatorPlugin.createErrorStatus(0, e.getLocalizedMessage(), e);
				}
				
				if (refactoringStatus.hasEntries()) {
					Dialog dialog= RefactoringUI.createLightWeightStatusDialog(refactoringStatus, getShell(), WorkbenchNavigatorMessages.MoveResourceAction_title);
					int result = dialog.open();
					if (result != IStatus.OK)
						return Status.CANCEL_STATUS;
				}
				
				final PerformRefactoringOperation op = new PerformRefactoringOperation(refactoring,
						CheckConditionsOperation.ALL_CONDITIONS);

				final IWorkspaceRunnable r = new IWorkspaceRunnable() {
					@Override
					public void run(IProgressMonitor monitor) throws CoreException {
						op.run(monitor);
					}
				};

				returnStatus = null;
				IRunnableWithProgress refactorOp = new IRunnableWithProgress() {
					@Override
					public void run(IProgressMonitor monitor) {
						try {
							ResourcesPlugin.getWorkspace().run(r, ResourcesPlugin.getWorkspace().getRoot(), IWorkspace.AVOID_UPDATE, monitor);
						} catch (CoreException ex) {
							returnStatus = WorkbenchNavigatorPlugin.createErrorStatus(0, ex.getLocalizedMessage(), ex);
						}
					}
				};
				
				if (returnStatus != null)
					return returnStatus;
				
				try {
					PlatformUI.getWorkbench().getProgressService().run(false, false, refactorOp);
				} catch (InterruptedException e) {
					return Status.CANCEL_STATUS;
				} catch (InvocationTargetException e) {
					return WorkbenchNavigatorPlugin.createErrorStatus(0, e.getLocalizedMessage(), e);
				}

			} catch (CoreException ex) {
				return WorkbenchNavigatorPlugin.createErrorStatus(0, ex.getLocalizedMessage(), ex);
			} catch (OperationCanceledException e) {
			}
		}

		return problems;
	}

	private IStatus performFileDrop(final CommonDropAdapter anAdapter, Object data) {
		final int currentOperation = anAdapter.getCurrentOperation();
		MultiStatus problems = new MultiStatus(PlatformUI.PLUGIN_ID, 0,
				WorkbenchNavigatorMessages.DropAdapter_problemImporting, null);
		mergeStatus(problems,
				validateTarget(anAdapter.getCurrentTarget(), anAdapter
						.getCurrentTransfer(), currentOperation));

		final IContainer target = getActualTarget((IResource) anAdapter
				.getCurrentTarget());
		final String[] names = (String[]) data;
		Display.getCurrent().asyncExec(new Runnable() {
			@Override
			public void run() {
				getShell().forceActive();
				new CopyFilesAndFoldersOperation(getShell()).copyOrLinkFiles(names, target, currentOperation);
			}
		});
		return problems;
	}

	private IStatus validateTarget(Object target, TransferData transferType,
			int dropOperation) {
		if (!(target instanceof IResource)) {
			return WorkbenchNavigatorPlugin
					.createInfoStatus(WorkbenchNavigatorMessages.DropAdapter_targetMustBeResource);
		}
		IResource resource = (IResource) target;
		if (!resource.isAccessible()) {
			return WorkbenchNavigatorPlugin
					.createErrorStatus(WorkbenchNavigatorMessages.DropAdapter_canNotDropIntoClosedProject);
		}
		IContainer destination = getActualTarget(resource);
		if (destination.getType() == IResource.ROOT) {
			return WorkbenchNavigatorPlugin
					.createErrorStatus(WorkbenchNavigatorMessages.DropAdapter_resourcesCanNotBeSiblings);
		}
		String message = null;
		if (LocalSelectionTransfer.getTransfer().isSupportedType(transferType)) {
			IResource[] selectedResources = getSelectedResources();

			if (selectedResources.length == 0) {
				message = WorkbenchNavigatorMessages.DropAdapter_dropOperationErrorOther;
			} else {
				CopyFilesAndFoldersOperation operation;
				if ((dropOperation == DND.DROP_COPY) || (dropOperation == DND.DROP_LINK)) {
					operation = new CopyFilesAndFoldersOperation(getShell());
					if (operation.validateDestination(destination, selectedResources) != null) {
						operation.setVirtualFolders(true);
						message = operation.validateDestination(destination, selectedResources);
					}
				} else {
					operation = new MoveFilesAndFoldersOperation(getShell());
					if (operation.validateDestination(destination, selectedResources) != null) {
						operation.setVirtualFolders(true);
						message = operation.validateDestination(destination, selectedResources);
					}
				}
			}
		} // file import?
		else if (FileTransfer.getInstance().isSupportedType(transferType)) {
			String[] sourceNames = (String[]) FileTransfer.getInstance()
					.nativeToJava(transferType);
			if (sourceNames == null) {
				sourceNames = new String[0];
			}
			CopyFilesAndFoldersOperation copyOperation = new CopyFilesAndFoldersOperation(
					getShell());
			message = copyOperation.validateImportDestination(destination,
					sourceNames);
		}
		if (message != null) {
			return WorkbenchNavigatorPlugin.createErrorStatus(message);
		}
		return Status.OK_STATUS;
	}

	private void mergeStatus(MultiStatus status, IStatus toMerge) {
		if (!toMerge.isOK()) {
			status.merge(toMerge);
		}
	}

	private void openError(IStatus status) {
		if (status == null) {
			return;
		}

		String genericTitle = WorkbenchNavigatorMessages.DropAdapter_title;
		int codes = IStatus.ERROR | IStatus.WARNING;

		if (!status.isMultiStatus()) {
			ErrorDialog
					.openError(getShell(), genericTitle, null, status, codes);
			return;
		}

		IStatus[] children = status.getChildren();
		if (children.length == 1) {
			ErrorDialog.openError(getShell(), status.getMessage(), null,
					children[0], codes);
			return;
		}
		ErrorDialog.openError(getShell(), genericTitle, null, status, codes);
	}

}
