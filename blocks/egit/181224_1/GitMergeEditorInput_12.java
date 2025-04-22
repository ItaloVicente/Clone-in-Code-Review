	@Override
	protected void contentsCreated() {
		super.contentsCreated();
		getNavigator().selectChange(true);
	}

	@Override
	public Viewer findContentViewer(Viewer oldViewer, ICompareInput input,
			Composite parent) {
		Viewer newViewer = super.findContentViewer(oldViewer, input, parent);
		ToolBarManager manager = CompareViewerPane.getToolBarManager(parent);
		if (manager != null) {
			setToggleCurrentChangesAction(manager, newViewer, input);
		}
		return newViewer;
	}

	@FunctionalInterface
	private interface ActionSupplier {
		public CompareEditorInputViewerAction get(boolean create);
	}

	private void setToggleCurrentChangesAction(ToolBarManager manager,
			Viewer newViewer, ICompareInput input) {
		boolean isApplicable = newViewer instanceof ContentMergeViewer
				&& input instanceof MergeDiffNode
				&& input.getAncestor() != null;
		setAction(manager, newViewer, isApplicable,
				ToggleCurrentChangesAction.COMMAND_ID,
				create -> {
					if (toggleCurrentChanges == null && create) {
						toggleCurrentChanges = new ToggleCurrentChangesAction(
								UIText.GitMergeEditorInput_ToggleCurrentChangesLabel,
								this);
						toggleCurrentChanges
								.setId(ToggleCurrentChangesAction.COMMAND_ID);
					}
					return toggleCurrentChanges;
				});
	}

	private void setAction(ToolBarManager manager, Viewer viewer,
			boolean isApplicable, String id, ActionSupplier supplier) {
		IContributionItem item = manager.find(id);
		if (item != null) {
			if (item instanceof ActionContributionItem) {
				IAction action = ((ActionContributionItem) item).getAction();
				if (action instanceof CompareEditorInputViewerAction) {
					((CompareEditorInputViewerAction) action).setViewer(
							isApplicable ? (ContentMergeViewer) viewer : null);
					action.setEnabled(isApplicable);
					if (item.isVisible() != isApplicable) {
						item.setVisible(isApplicable);
						manager.update(true);
					}
				}
			}
		} else if (isApplicable) {
			CompareEditorInputViewerAction action = supplier.get(true);
			action.setViewer((ContentMergeViewer) viewer);
			action.setEnabled(true);
			manager.insert(0, new ActionContributionItem(action));
			manager.update(true);
			registerAction(action, id);
		} else {
			CompareEditorInputViewerAction action = supplier.get(false);
			if (action != null) {
				action.setEnabled(false);
			}
		}
	}

	private void registerAction(IAction action, String commandId) {
		if (activations.containsKey(commandId)) {
			return;
		}
		action.setActionDefinitionId(commandId);
		IServiceLocator locator = getContainer().getServiceLocator();
		if (locator != null) {
			IHandlerService handlers = locator
					.getService(IHandlerService.class);
			if (handlers != null) {
				activations.put(commandId, handlers.activateHandler(commandId,
						new ActionHandler(action)));
			}
		}
	}

	@Override
	protected void handleDispose() {
		super.handleDispose();
		activations.values()
				.forEach(a -> a.getHandlerService().deactivateHandler(a));
		activations.clear();
		if (toggleCurrentChanges != null) {
			toggleCurrentChanges.dispose();
			toggleCurrentChanges = null;
		}
		PlatformUI.getWorkbench().getDisplay().asyncExec(this::cleanUp);
	}

	private void cleanUp() {
		if (toDelete == null || toDelete.isEmpty()) {
			return;
		}
		List<IFile> toClean = toDelete;
		toDelete = null;
		Job job = new Job(UIText.GitMergeEditorInput_ResourceCleanupJobName) {

			@Override
			public boolean shouldSchedule() {
				return super.shouldSchedule()
						&& !PlatformUI.getWorkbench().isClosing();
			}

			@Override
			public boolean shouldRun() {
				return super.shouldRun()
						&& !PlatformUI.getWorkbench().isClosing();
			}

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				IWorkspaceRunnable remove = m -> {
					SubMonitor progress = SubMonitor.convert(m, toClean.size());
					for (IFile tmp : toClean) {
						if (PlatformUI.getWorkbench().isClosing()) {
							return;
						}
						try {
							tmp.delete(true, progress.newChild(1));
						} catch (CoreException e) {
						}
					}
				};
				try {
					ResourcesPlugin.getWorkspace().run(remove, null,
							IWorkspace.AVOID_UPDATE, monitor);
				} catch (CoreException e) {
					return e.getStatus();
				}
				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.setUser(false);
		job.schedule();
	}

