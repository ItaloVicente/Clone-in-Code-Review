package org.eclipse.ui.tests.dialogs;

import junit.framework.TestCase;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.EditorSelectionDialog;
import org.eclipse.ui.dialogs.ListSelectionDialog;
import org.eclipse.ui.dialogs.ProjectLocationSelectionDialog;
import org.eclipse.ui.dialogs.SaveAsDialog;
import org.eclipse.ui.dialogs.TypeFilteringDialog;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.AboutDialog;
import org.eclipse.ui.internal.dialogs.FileExtensionDialog;
import org.eclipse.ui.internal.dialogs.SavePerspectiveDialog;
import org.eclipse.ui.internal.dialogs.SelectPerspectiveDialog;
import org.eclipse.ui.internal.dialogs.ShowViewDialog;
import org.eclipse.ui.internal.registry.PerspectiveRegistry;
import org.eclipse.ui.internal.views.navigator.ResourceNavigatorMessages;
import org.eclipse.ui.tests.harness.util.DialogCheck;

public class UIDialogs extends TestCase {
    private static final String PROJECT_SELECTION_MESSAGE = "Select Project";

    private static final String FILTER_SELECTION_MESSAGE = ResourceNavigatorMessages.FilterSelection_message;

    public UIDialogs(String name) {
        super(name);
    }

    private Shell getShell() {
        return DialogCheck.getShell();
    }

    private IWorkbench getWorkbench() {
        return PlatformUI.getWorkbench();
    }

    public void testAbout() {
        Dialog dialog = null;
        dialog = new AboutDialog(getShell());
        DialogCheck.assertDialog(dialog, this);
    }

    public void testAddProjects() {
		Dialog dialog = new ListSelectionDialog(getShell(), null, ArrayContentProvider.getInstance(),
				new LabelProvider(), PROJECT_SELECTION_MESSAGE);
        DialogCheck.assertDialog(dialog, this);
    }

    public void testCopyMoveProject() {
        IProject dummyProject = ResourcesPlugin.getWorkspace().getRoot()
                .getProject("DummyProject");
        Dialog dialog = new ProjectLocationSelectionDialog(getShell(),
                dummyProject);
        DialogCheck.assertDialog(dialog, this);
    }

    public void testCopyMoveResource() {
        Dialog dialog = new ContainerSelectionDialog(getShell(), null, true,
                "Select Destination");
        DialogCheck.assertDialog(dialog, this);
    }

    public void testEditActionSetsDialog() {
    	fail("CustomizePerspectiveDialog not implemented");
    }

    public void testEditorSelection() {
        Dialog dialog = new EditorSelectionDialog(getShell());
        DialogCheck.assertDialog(dialog, this);
    }

    public void testNavigatorFilter() {
		Dialog dialog = new ListSelectionDialog(getShell(), null, ArrayContentProvider.getInstance(),
				new LabelProvider(), FILTER_SELECTION_MESSAGE);
        DialogCheck.assertDialog(dialog, this);
    }

    public void testNewFileType() {
        Dialog dialog = new FileExtensionDialog(getShell());
        DialogCheck.assertDialog(dialog, this);
    }

    public void testProgressInformation() {
        ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
        dialog.setBlockOnOpen(true);
        DialogCheck.assertDialog(dialog, this);
    }

    public void testSaveAs() {
        Dialog dialog = new SaveAsDialog(getShell());
        DialogCheck.assertDialog(dialog, this);
    }

    public void testSavePerspective() {
        PerspectiveRegistry reg = (PerspectiveRegistry) WorkbenchPlugin
                .getDefault().getPerspectiveRegistry();
        SavePerspectiveDialog dialog = new SavePerspectiveDialog(getShell(),
                reg);
        IPerspectiveDescriptor description = reg
                .findPerspectiveWithId(getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage()
                        .getPerspective().getId());
        dialog.setInitialSelection(description);
        DialogCheck.assertDialog(dialog, this);
    }

	public void testLoadNotExistingPerspective() {
    	fail("PerspectiveRegistry.getCustomPersp not implemented");
    }

    public void testSelectPerspective() {
        Dialog dialog = new SelectPerspectiveDialog(getShell(), PlatformUI
                .getWorkbench().getPerspectiveRegistry());
        DialogCheck.assertDialog(dialog, this);
    }

    public void testSelectTypes() {
        Dialog dialog = new TypeFilteringDialog(getShell(), null);
        DialogCheck.assertDialog(dialog, this);
    }

    public void testShowView() {

    	IWorkbench workbench = getWorkbench();

    	Shell shell = workbench.getActiveWorkbenchWindow().getShell();
		IEclipseContext ctx = workbench.getService(IEclipseContext.class);
		EModelService modelService = workbench.getService(EModelService.class);
		EPartService partService = workbench.getService(EPartService.class);
		MApplication app = workbench.getService(MApplication.class);
		MWindow window = workbench.getService(MWindow.class);
		Dialog dialog = new ShowViewDialog(shell, app, window, modelService, partService, ctx);
        DialogCheck.assertDialog(dialog, this);
    }
}
