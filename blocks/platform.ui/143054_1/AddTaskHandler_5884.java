package org.eclipse.ui.internal.views.helpers;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.NewProjectAction;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.internal.IWorkbenchGraphicConstants;
import org.eclipse.ui.internal.WorkbenchImages;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.dialogs.WorkbenchWizardElement;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.navigator.wizards.WizardShortcutAction;
import org.eclipse.ui.internal.views.navigator.ResourceNavigatorMessages;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.eclipse.ui.wizards.IWizardRegistry;

public final class EmptyWorkspaceHelper {

	private Composite parent;
	private Composite emptyArea;
	private StackLayout layout;
	private Control control;
	private Composite displayArea;
	private ArrayList<IAction> projectWizardActions;
	private IAction newProjectAction;
	private IAction importAction;
	private LocalResourceManager resourceManager;

	public Composite getComposite(Composite aParent) {
		this.parent = aParent;
		displayArea = new Composite(parent, SWT.NONE);

		layout = new StackLayout();
		displayArea.setLayout(layout);
		createEmptyArea(displayArea);

		registerListeners();
		return displayArea;
	}

	public void setNonEmptyControl(Control control) {
		this.control = control;
		emptyArea.setBackground(control.getBackground());
		switchTopControl();
	}

	private void dispose(Listener listener) {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().removePerspectiveListener(listener);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(listener);
		JFaceResources.getColorRegistry().removeListener(listener);
		parent.removeDisposeListener(listener);

		parent = null;
		emptyArea = null;
		layout = null;
		control = null;
		displayArea = null;
		projectWizardActions = null;
		newProjectAction = null;
		importAction = null;
	}

	private void registerListeners() {
		Listener listener = new Listener();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().addPerspectiveListener(listener);
		ResourcesPlugin.getWorkspace().addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
		JFaceResources.getColorRegistry().addListener(listener);
		parent.addDisposeListener(listener);
	}

	private void createEmptyArea(Composite displayAreas) {
		if (newProjectAction == null) {
			newProjectAction = new NewProjectAction();
		}
		if (importAction == null) {
			importAction = new ImportAction();
		}
		if (projectWizardActions == null) {
			projectWizardActions = new ArrayList<>();
			readProjectWizardActions();
		}

		emptyArea = new Composite(displayAreas, SWT.NONE);
		resourceManager = new LocalResourceManager(JFaceResources.getResources(), emptyArea);

		emptyArea.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().applyTo(emptyArea);
		Composite infoArea = new Composite(emptyArea, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP).grab(true, true).indent(5, 5).applyTo(infoArea);
		GridLayoutFactory.swtDefaults().applyTo(infoArea);
		Link messageLabel = new Link(infoArea, SWT.WRAP);

		Composite optionsArea = null;
		messageLabel.setText(ResourceNavigatorMessages.EmptyWorkspaceHelper_noProjectsAvailable);
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(messageLabel);

		optionsArea = new Composite(infoArea, SWT.NONE);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(optionsArea);
		GridDataFactory.swtDefaults().indent(5, 0).grab(true, true).applyTo(optionsArea);

		final FormToolkit toolkit = new FormToolkit(emptyArea.getDisplay());
		emptyArea.addDisposeListener(e -> toolkit.dispose());
		final Color linkColor = JFaceColors.getHyperlinkText(emptyArea.getDisplay());

		for (IAction action : projectWizardActions) {
			String description = action.getDescription();
			if (description == null || description.isEmpty()) {
				description = action.getText();
			}
			createOption(optionsArea, toolkit, linkColor, action, action.getImageDescriptor(), description);
		}
		createOption(optionsArea, toolkit, linkColor, newProjectAction, newProjectAction.getImageDescriptor(),
				ResourceNavigatorMessages.EmptyWorkspaceHelper_createProject);
		createOption(optionsArea, toolkit, linkColor, importAction,
				WorkbenchImages.getImageDescriptor(IWorkbenchGraphicConstants.IMG_ETOOL_IMPORT_WIZ),
				ResourceNavigatorMessages.EmptyWorkspaceHelper_importProjects);
	}

	private void recreateEmptyArea() {
		disposeEmptyArea();

		createEmptyArea(displayArea);
		if (control != null && !control.isDisposed()) {
			emptyArea.setBackground(control.getBackground());
		}
	}

	private void disposeEmptyArea() {
		if (emptyArea != null) {
			emptyArea.dispose();
			emptyArea = null;
		}
	}

	private void readProjectWizardActions() {
		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchWindow win = wb.getActiveWorkbenchWindow();
		IWorkbenchPage page = win.getActivePage();
		String[] wizardIds = page.getNewWizardShortcuts();
		projectWizardActions.clear();
		for (String wizardId : wizardIds) {
			IWizardRegistry newWizardRegistry = WorkbenchPlugin.getDefault().getNewWizardRegistry();
			IWizardDescriptor wizardDesc = newWizardRegistry.findWizard(wizardId);
			if (wizardDesc == null) {
				continue;
			}
			String[] tags = wizardDesc.getTags();
			for (String tag : tags) {
				if (WorkbenchWizardElement.TAG_PROJECT.equals(tag)) {
					IAction action = getAction(newWizardRegistry, wizardId);
					if (action != null) {
						projectWizardActions.add(action);
					}
				}
			}
		}
	}

	private void createOption(Composite optionsArea, final FormToolkit toolkit, final Color linkColor, IAction action,
			ImageDescriptor imageDesc, String text) {
		Label addLabel = new Label(optionsArea, SWT.NONE);

		if (imageDesc == null) {
			ISharedImages images = PlatformUI.getWorkbench().getSharedImages();
			imageDesc = images.getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD);
		}
		addLabel.setImage(resourceManager.createImage(imageDesc));

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

	private Runnable switchTopControlRunnable = () -> {
		if (switchTopControl()) {
			displayArea.requestLayout();
		}
	};

	private boolean switchTopControl() {
		if (control == null || control.isDisposed()) {
			return false;
		}
		Control oldTop = layout.topControl;
		IProject[] projs = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		if (projs.length > 0) {
			layout.topControl = control;
			disposeEmptyArea();
		} else {
			if (emptyArea == null || emptyArea.isDisposed()) {
				recreateEmptyArea();
			}
			layout.topControl = emptyArea;
		}
		return oldTop != layout.topControl;
	}

	private class Listener
			implements IResourceChangeListener, IPerspectiveListener, IPropertyChangeListener, DisposeListener {

		@Override
		public void resourceChanged(IResourceChangeEvent event) {

			IResourceDelta resourceDelta = event.getDelta();
			if (resourceDelta != null) {

				IResourceDelta[] affectedChildren = resourceDelta.getAffectedChildren();
				for (IResourceDelta affectedChildResourceDelta : affectedChildren) {
					IResource resource = affectedChildResourceDelta.getResource();
					int kind = affectedChildResourceDelta.getKind();
					if (resource instanceof IProject
							&& (kind == IResourceDelta.ADDED || kind == IResourceDelta.REMOVED)) {
						PlatformUI.getWorkbench().getDisplay().asyncExec(
								() -> PlatformUI.getWorkbench().getDisplay().timerExec(200, switchTopControlRunnable));
						return;
					}
				}
			}
		}

		@Override
		public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {
			readProjectWizardActions();
			if (emptyArea != null) {
				recreateEmptyArea();
				switchTopControlRunnable.run();
			}
		}

		@Override
		public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective, String changeId) {
		}

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (emptyArea != null && JFacePreferences.HYPERLINK_COLOR.equals(event.getProperty())) {
				recreateEmptyArea();
				switchTopControlRunnable.run();
			}
		}

		@Override
		public void widgetDisposed(DisposeEvent e) {
			dispose(this);
		}

	}

	private static class ImportAction extends Action {
		@Override
		public void run() {
			IHandlerService handlerService = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getService(IHandlerService.class);
			try {
				handlerService.executeCommand("org.eclipse.ui.file.import", null); //$NON-NLS-1$
			} catch (Exception ex) {
				IDEWorkbenchPlugin.log(this.getClass(), "run", ex); //$NON-NLS-1$
			}
		}
	}

}
