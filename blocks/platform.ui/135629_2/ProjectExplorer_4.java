	@Override
	protected CommonViewer createCommonViewer(Composite aParent) {
		viewer = super.createCommonViewer(aParent);

		emptyArea.setBackground(viewer.getControl().getBackground());
		switchTopControl();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this,
				IResourceChangeEvent.POST_CHANGE);

		return viewer;
	}

	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().removePerspectiveListener(this);
		super.dispose();
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
			projectWizardActions = new ArrayList<IAction>();
			readProjectWizardActions();
		}

		PlatformUI.getWorkbench().getActiveWorkbenchWindow().addPerspectiveListener(this);

		emptyArea = new Composite(parent, SWT.NONE);
		emptyArea.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().applyTo(emptyArea);
		Composite infoArea = new Composite(emptyArea, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.LEFT, SWT.TOP).grab(true, true).indent(20, 20).applyTo(infoArea);
		GridLayoutFactory.swtDefaults().applyTo(infoArea);
		Link messageLabel = new Link(infoArea, SWT.WRAP);
		messageLabel.setText(WorkbenchNavigatorMessages.ProjectExplorer_noProjectsAvailable);
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
			createOption(optionsArea, toolkit, linkColor, action, action.getDescription());
		}
		createOption(optionsArea, toolkit, linkColor, newProjectAction,
				WorkbenchNavigatorMessages.ProjectExplorer_createGeneralProject);
		createOption(optionsArea, toolkit, linkColor, importAction,
				WorkbenchNavigatorMessages.ProjectExplorer_importProject);
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
			String text) {
		Label addLabel = new Label(optionsArea, SWT.NONE);
		addLabel.setImage(action.getImageDescriptor().createImage());
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
			layout.topControl = viewer.getControl();
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

