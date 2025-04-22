		if (revisionRuler != null) {
			if (revisionRuler instanceof IRevisionRulerColumnExtension) {
				String flagName = getClass().getName() + ".selectionHandler"; //$NON-NLS-1$
				Control control = revisionRuler.getControl();
				Object flag = control.getData(flagName);
				if (flag == null) {
					((IRevisionRulerColumnExtension) revisionRuler)
							.getRevisionSelectionProvider()
							.addSelectionChangedListener(
									new RevisionSelectionHandler(repository,
											path, storage));
					control.setData(flagName, Boolean.TRUE);
				}
			}
			if (currentHead != null && storage instanceof IFile
					&& editor.isChangeInformationShowing()) {
				refreshOnHeadChange(editor, revisionRuler, creator,
						currentHead);
			}
		}
	}

	private void refreshOnHeadChange(AbstractDecoratedTextEditor editor,
			IRevisionRulerColumn ruler,
			IInformationControlCreator hoverPopupCreator,
			ObjectId currentHead) {
		String flagName = getClass().getName() + ".editorHooks"; //$NON-NLS-1$
		Control control = ruler.getControl();
		Object flag = control.getData(flagName);
		if (flag != null) {
			return;
		}
		EditorVisibilityTracker visibilityTracker = new EditorVisibilityTracker(
				editor);
		IPartService partService = editor.getEditorSite()
				.getService(IPartService.class);
		partService.addPartListener(visibilityTracker);
		RefsChangedListener refsTracker = new RefsChangedListener() {

			private ObjectId lastHead = currentHead;

			@Override
			public void onRefsChanged(RefsChangedEvent event) {
				try {
					ObjectId head = event.getRepository()
							.resolve(Constants.HEAD);
					if (!lastHead.equals(head)) {
						lastHead = head;
						Display display = PlatformUI.getWorkbench()
								.getDisplay();
						if (display != null && !display.isDisposed()) {
							display.asyncExec(() -> {
								if (editor.isChangeInformationShowing()) {
									visibilityTracker.runWhenVisible(
											() -> updateBlame(head, ruler,
													hoverPopupCreator, editor));
								}
							});
						}
					}
				} catch (IOException e) {
					Activator.logError(e.getLocalizedMessage(), e);
				}
			}
		};
		ListenerHandle handle = repository.getListenerList()
				.addRefsChangedListener(refsTracker);
		control.setData(flagName, Boolean.TRUE);
		IAction existingAction = editor
				.getAction(ITextEditorActionConstants.REVISION_HIDE_INFO);
		Action newAction = new Action(existingAction.getText()) {

			@Override
			public void run() {
				control.setData(flagName, null);
				handle.remove();
				partService.removePartListener(visibilityTracker);
				editor.setAction(ITextEditorActionConstants.REVISION_HIDE_INFO,
						existingAction);
				existingAction.run();
			}
		};
		newAction.setToolTipText(existingAction.getToolTipText());
		newAction.setDescription(existingAction.getDescription());
		newAction.setImageDescriptor(existingAction.getImageDescriptor());
		editor.setAction(ITextEditorActionConstants.REVISION_HIDE_INFO,
				newAction);
		ruler.getControl().addDisposeListener(event -> {
			handle.remove();
			partService.removePartListener(visibilityTracker);
		});
	}

	private void updateBlame(ObjectId head, IRevisionRulerColumn ruler,
			IInformationControlCreator hoverPopupCreator,
			AbstractDecoratedTextEditor editor) {
		Job blamer = new Job(UIText.ShowBlameHandler_JobName) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					RevisionInformation info = computeRevisions(repository,
							head, path, monitor);
					Control control = ruler.getControl();
					Display display = control.getDisplay();
					display.asyncExec(() -> {
						if (!control.isDisposed()) {
							info.setHoverControlCreator(hoverPopupCreator);
							info.setInformationPresenterControlCreator(
									hoverPopupCreator);
							if (editor.isChangeInformationShowing()) {
								editor.showRevisionInformation(info,
										QUICKDIFF_PROVIDER_ID);
							}
						}
					});
					return Status.OK_STATUS;
				} catch (SWTException e) {
					return Status.CANCEL_STATUS;
				} catch (RuntimeException e) {
					return Activator.createErrorStatus(e.getLocalizedMessage(),
							e);
				} finally {
					monitor.done();
				}
			}

			@Override
			public boolean belongsTo(Object family) {
				return JobFamilies.BLAME == family || super.belongsTo(family);
			}
		};
		blamer.setUser(false);
		blamer.setSystem(true);
		blamer.schedule();
