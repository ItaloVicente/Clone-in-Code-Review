			final IPropertyChangeListener[] perfs = new IPropertyChangeListener[1];
			final IPartListener partListener = new IPartListener() {

				@Override
				public void partOpened(IWorkbenchPart part) {
				}

				@Override
				public void partDeactivated(IWorkbenchPart part) {
				}

				@Override
				public void partClosed(IWorkbenchPart part) {
				}

				@Override
				public void partBroughtToTop(IWorkbenchPart part) {
					if (!(part instanceof IEditorPart)) {
						return;
					}
					ICommandService commandService = window
							.getService(ICommandService.class);

					commandService.refreshElements(COMMAND_ID, null);
				}

				@Override
				public void partActivated(IWorkbenchPart part) {
				}
			};
			window.getPartService().addPartListener(partListener);
			final CommandContributionItem action = new CommandContributionItem(
					parameter) {
				@Override
				public void dispose() {
					WorkbenchPlugin.getDefault().getPreferenceStore()
							.removePropertyChangeListener(perfs[0]);
					window.getPartService().removePartListener(partListener);
					super.dispose();
				}
			};

			perfs[0] = new IPropertyChangeListener() {
				@Override
				public void propertyChange(PropertyChangeEvent event) {
					if (event.getProperty().equals(
							IPreferenceConstants.REUSE_EDITORS_BOOLEAN)) {
						if (action.getParent() != null) {
							IPreferenceStore store = WorkbenchPlugin
									.getDefault().getPreferenceStore();
							boolean reuseEditors = store
									.getBoolean(IPreferenceConstants.REUSE_EDITORS_BOOLEAN);
							action.setVisible(reuseEditors);
							action.getParent().markDirty();
							if (window.getShell() != null
									&& !window.getShell().isDisposed()) {
								window.getShell().getDisplay().syncExec(
										new Runnable() {
											@Override
											public void run() {
												action.getParent()
														.update(false);
											}
										});
							}
						}
					}
				}
			};
			WorkbenchPlugin.getDefault().getPreferenceStore()
					.addPropertyChangeListener(perfs[0]);
