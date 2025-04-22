package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.wizards;

import java.util.Collections;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleagueFactory;
import org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.HockeyleaguePackage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.ISetSelectionTarget;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

public class NewHockeyleagueFileWizard
	extends BasicNewResourceWizard {

	private NewHockeyleagueFileWizardPage mainPage;

	private IStructuredSelection selection;

	private IWorkbench workbench;

	public void addPages() {
		mainPage = new NewHockeyleagueFileWizardPage("Whatever", selection); //$NON-NLS-1$
		mainPage.setTitle("Hockey League Example File"); //$NON-NLS-1$
		mainPage.setDescription("Create a new Tabbed Properties View Hockey League Example File"); //$NON-NLS-1$
		mainPage.setFileName("example.hockeyleague"); //$NON-NLS-1$
		addPage(mainPage);

		if (selection != null && !selection.isEmpty()) {
			Object selectedElement = selection.iterator().next();
			if (selectedElement instanceof IResource) {
				IResource selectedResource = (IResource) selectedElement;
				if (selectedResource.getType() == IResource.FILE) {
					selectedResource = selectedResource.getParent();
				}

				if (selectedResource instanceof IFolder
					|| selectedResource instanceof IProject) {
					mainPage.setContainerFullPath(selectedResource
						.getFullPath());

					String defaultFilename = "example"; //$NON-NLS-1$
					String defaultExtension = "hockeyleague"; //$NON-NLS-1$
					String modelFilename = defaultFilename + "." //$NON-NLS-1$
						+ defaultExtension;
					for (int i = 1; ((IContainer) selectedResource)
						.findMember(modelFilename) != null; ++i) {
						modelFilename = defaultFilename + i + "." //$NON-NLS-1$
							+ defaultExtension;
					}
					mainPage.setFileName(modelFilename);
				}
			}
		}
	}

	public void init(IWorkbench aWorkbench,
			IStructuredSelection currentSelection) {
		super.init(aWorkbench, currentSelection);
		this.workbench = aWorkbench;
		this.selection = currentSelection;
		setWindowTitle("New Hockey League File"); //$NON-NLS-1$
		setNeedsProgressMonitor(true);
	}

	public boolean performFinish() {
		try {
			final IFile hockeyleagueFile = getHockeyleagueFile();

			WorkspaceModifyOperation operation = new WorkspaceModifyOperation() {

				protected void execute(IProgressMonitor progressMonitor) {
					try {
						ResourceSet resourceSet = new ResourceSetImpl();

						URI fileURI = URI
							.createPlatformResourceURI(hockeyleagueFile
								.getFullPath().toString());

						Resource resource = resourceSet.createResource(fileURI);

						EObject rootObject = createInitialModel();
						if (rootObject != null) {
							resource.getContents().add(rootObject);
						}

						resource.save(Collections.EMPTY_MAP);
					} catch (Exception exception) {
						exception.printStackTrace();
					} finally {
						progressMonitor.done();
					}
				}
			};

			getContainer().run(false, false, operation);

			IWorkbenchWindow workbenchWindow = workbench
				.getActiveWorkbenchWindow();
			IWorkbenchPage page = workbenchWindow.getActivePage();
			final IWorkbenchPart activePart = page.getActivePart();
			if (activePart instanceof ISetSelectionTarget) {
				final ISelection targetSelection = new StructuredSelection(
					hockeyleagueFile);
				getShell().getDisplay().asyncExec(new Runnable() {

					public void run() {
						((ISetSelectionTarget) activePart)
							.selectReveal(targetSelection);
					}
				});
			}

			try {
				IDE.openEditor(page, hockeyleagueFile, true);
			} catch (PartInitException exception) {
				MessageDialog.openError(workbenchWindow.getShell(),
					"Open Editor", exception.getMessage()); //$NON-NLS-1$
				return false;
			}

			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
			return false;
		}
	}

	public IFile getHockeyleagueFile() {
		return mainPage.getHockeyleagueFile();
	}

	private EObject createInitialModel() {
		EClass eClass = HockeyleaguePackage.eINSTANCE.getLeague();
		EObject rootObject = HockeyleagueFactory.eINSTANCE.create(eClass);
		return rootObject;
	}
}
