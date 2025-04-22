package org.eclipse.ui.views.navigator;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.NewProjectAction;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.internal.navigator.wizards.WizardShortcutAction;
import org.eclipse.ui.internal.views.navigator.ResourceNavigatorMessages;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

public class EmptyWorkspaceHelper implements IResourceChangeListener, IPerspectiveListener {

	private Composite emptyArea;
	private StackLayout layout;
	private Control control;
	private Composite displayArea;
	private ArrayList<IAction> projectWizardActions = null;
	private IAction newProjectAction = null;
	private IAction importAction = null;

	public EmptyWorkspaceHelper(Composite parent) {
		displayArea = parent;

		layout = new StackLayout();
		displayArea.setLayout(layout);
		createEmptyArea(displayArea);

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().addPerspectiveListener(this);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	public void setControl(Control viewer) {
		this.control = viewer;
		emptyArea.setBackground(viewer.getBackground());
		switchTopControl();
	}

	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().removePerspectiveListener(this);
	}


	private void createEmptyArea(Composite parent) {
		if (newProjectAction == null) {
			newProjectAction = new NewProjectAction();
		}
		if (importAction == null) {
			importAction = getAction(WorkbenchPlugin.getDefault().getImportWizardRegistry(),
					"org.eclipse.ui.wizards.import.ExternalProject"); //$NON-NLS-1$
		}
		if (projectWizardActions == null) {
			projectWizardActions = new ArrayList<>();
			readProjectWizardActions();
		}


		emptyArea = new Composite(parent, SWT.NONE);
		emptyArea.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().applyTo(emptyArea);
		Composite infoArea = new Composite(emptyArea, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP).grab(true, true).indent(16, 16).applyTo(infoArea);
		GridLayoutFactory.swtDefaults().applyTo(infoArea);
		Link messageLabel = new Link(infoArea, SWT.WRAP);
		messageLabel.setText(ResourceNavigatorMessages.EmptyWorkspaceHelper_noProjectsAvailable);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(messageLabel);

		Composite optionsArea = new Composite(infoArea, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(optionsArea);
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, true).applyTo(optionsArea);

		final FormToolkit toolkit = new FormToolkit(emptyArea.getDisplay());
		emptyArea.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		final Color linkColor = JFaceColors.getHyperlinkText(emptyArea.getDisplay());

		for (IAction action : projectWizardActions) {
			createOption(optionsArea, toolkit, linkColor, action, action.getImageDescriptor().createImage(),
					action.getDescription());
		}

		createOption(optionsArea, toolkit, linkColor, newProjectAction,
				newProjectAction.getImageDescriptor().createImage(),
				ResourceNavigatorMessages.EmptyWorkspaceHelper_createGeneralProject);
		createOption(optionsArea, toolkit, linkColor, importAction,
				WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_IMPORT_WIZ).createImage(),
				ResourceNavigatorMessages.EmptyWorkspaceHelper_ImportProjects);
	}

	private void readProjectWizardActions() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		String[] wizardIds = page.getNewWizardShortcuts();
		projectWizardActions.clear();
		for (String wizardId : wizardIds) {
			IWizardDescriptor wizardDesc = WorkbenchPlugin.getDefault().getNewWizardRegistry().findWizard(wizardId);
			if (wizardDesc == null)
				continue;
			String[] tags = wizardDesc.getTags();
			for (String tag : tags) {
				if (WorkbenchWizardElement.TAG_PROJECT.equals(tag)) {
					IAction action = getAction(WorkbenchPlugin.getDefault().getNewWizardRegistry(), wizardId);
					projectWizardActions.add(action);
				}
			}
		}
	}

	private void createOption(Composite optionsArea, final FormToolkit toolkit, final Color linkColor, IAction action,
			Image image, String text) {
		Label addLabel = new Label(optionsArea, SWT.NONE);
		addLabel.setImage(image);
		Hyperlink addLink = toolkit.createHyperlink(optionsArea, text, SWT.WRAP);
		addLink.setForeground(linkColor);
		addLink.addHyperlinkListener(new HyperlinkAdapter() {
			@Override
			public void linkActivated(HyperlinkEvent e) {
				action.run();
			}
		});
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(addLink);
	}

	private IAction getAction(IWizardRegistry registry, String id) {
		IWizardDescriptor wizardDesc = registry.findWizard(id);
		WizardShortcutAction action = null;
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		if (wizardDesc != null) {
			action = new WizardShortcutAction(win, wizardDesc);
		}
		return action;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {

		IResourceDelta resourceDelta = event.getDelta();
		if (resourceDelta != null) {

			IResourceDelta[] affectedChildren = resourceDelta.getAffectedChildren();
			for (IResourceDelta affectedChildResourceDelta : affectedChildren) {
				IResource resource = affectedChildResourceDelta.getResource();
				int kind = affectedChildResourceDelta.getKind();
				if (resource instanceof IProject && (kind == IResourceDelta.ADDED || kind == IResourceDelta.REMOVED)) {
					Display.getDefault().asyncExec(() -> Display.getDefault().timerExec(200, switchTopControlRunnable));
				}
			}
		}
	}

	private Runnable switchTopControlRunnable = () -> {
		boolean switched = switchTopControl();
		if (switched) {
			displayArea.requestLayout();
		}
	};

	private boolean switchTopControl() {
		Control oldTop = layout.topControl;
		IProject[] projs = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		if (projs.length > 0) {
			layout.topControl = control;
		} else {
			layout.topControl = emptyArea;
		}
		return oldTop != layout.topControl;
	}

	@Override
	public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
		if (emptyArea != null) {
			emptyArea.dispose();
			emptyArea = null;
		}
		readProjectWizardActions();
		createEmptyArea(displayArea);
		switchTopControlRunnable.run();
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
	}

}
