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
		super.dispose();
	}

	private void createEmptyArea(Composite parent) {
		emptyArea = new Composite(parent, SWT.NONE);
		emptyArea.setBackgroundMode(SWT.INHERIT_FORCE);
		GridLayoutFactory.fillDefaults().applyTo(emptyArea);
		Composite infoArea = new Composite(emptyArea, SWT.NONE);
		GridDataFactory.swtDefaults().align(SWT.CENTER, SWT.CENTER).grab(true, true).applyTo(infoArea);
		GridLayoutFactory.swtDefaults().applyTo(infoArea);
		Link messageLabel = new Link(infoArea, SWT.WRAP);
		messageLabel.setText("No projects available. Create a new project via the <a>new project wizard</a>..."); //$NON-NLS-1$
		GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(messageLabel);

		messageLabel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new NewProjectAction().run();
			}
		});
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
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							Display.getDefault().timerExec(200, switchTopControl);
						}
					});
				}
			}
		}
	}

	private Runnable switchTopControl = new Runnable() {
		@Override
		public void run() {
			boolean switched = switchTopControl();
			if (switched) {
				displayArea.requestLayout();
			}
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

