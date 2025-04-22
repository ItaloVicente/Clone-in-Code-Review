
	private static final class BranchMessageDialog extends MessageDialog {
		private final List<Ref> nodes;

		private BranchMessageDialog(Shell parentShell, List<Ref> nodes) {
			super(
					parentShell,
					UIText.DeleteBranchOnCommitHandler_ConfirmBranchDeletionDialogTitle,
					null,
					UIText.DeleteBranchOnCommitHandler_ConfirmBranchDeletionMessage,
					MessageDialog.QUESTION, new String[] {
							IDialogConstants.OK_LABEL,
							IDialogConstants.CANCEL_LABEL }, 0);
			this.nodes = nodes;
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			Composite area = new Composite(parent, SWT.NONE);
			GridDataFactory.fillDefaults().grab(true, true).span(2, 1)
					.applyTo(area);
			area.setLayout(new GridLayout(1, false));

			TableViewer branchesList = new TableViewer(area);
			GridDataFactory.fillDefaults().grab(true, true)
					.applyTo(branchesList.getTable());
			branchesList.setContentProvider(ArrayContentProvider.getInstance());
			branchesList.setLabelProvider(new GitLabelProvider());
			branchesList.setInput(nodes);
			return area;
		}

	}

	private static final class BranchSelectionDialog extends MessageDialog {
		private final List<Ref> nodes;

		private final List<Ref> selected = new ArrayList<Ref>();

		private FilteredCheckboxTree fTree;

		private BranchSelectionDialog(Shell parentShell, List<Ref> nodes) {
			super(
					parentShell,
					UIText.DeleteBranchOnCommitHandler_SelectBranchDialogTitle,
					null,
					UIText.DeleteBranchOnCommitHandler_SelectBranchDialogMessage,
					MessageDialog.QUESTION, new String[] {
							IDialogConstants.OK_LABEL,
							IDialogConstants.CANCEL_LABEL }, 0);
			this.nodes = nodes;
		}

		@Override
		public void create() {
			super.create();
			getButton(OK).setEnabled(false);
		}

		@Override
		protected Control createCustomArea(Composite parent) {
			Composite area = new Composite(parent, SWT.NONE);
			GridDataFactory.fillDefaults().grab(true, true).span(2, 1)
					.applyTo(area);
			area.setLayout(new GridLayout(1, false));
			fTree = new FilteredCheckboxTree(area, null, SWT.NONE,
					new PatternFilter()) {
				/*
				 * Overridden to check page when refreshing is done.
				 */
				protected WorkbenchJob doCreateRefreshJob() {
					WorkbenchJob refreshJob = super.doCreateRefreshJob();
					refreshJob.addJobChangeListener(new JobChangeAdapter() {
						public void done(IJobChangeEvent event) {
							if (event.getResult().isOK()) {
								getDisplay().asyncExec(new Runnable() {
									public void run() {
										checkPage();
									}
								});
							}
						}
					});
					return refreshJob;
				}
			};

			CachedCheckboxTreeViewer viewer = fTree.getCheckboxTreeViewer();
			GridDataFactory.fillDefaults().grab(true, true).applyTo(fTree);
			viewer.setContentProvider(new ITreeContentProvider() {
				public void inputChanged(Viewer actViewer, Object oldInput,
						Object newInput) {
				}

				public void dispose() {
				}

				public boolean hasChildren(Object element) {
					return false;
				}

				public Object getParent(Object element) {
					return null;
				}

				public Object[] getElements(Object inputElement) {
					return ((List) inputElement).toArray();
				}

				public Object[] getChildren(Object parentElement) {
					return null;
				}
			});

			viewer.addCheckStateListener(new ICheckStateListener() {
				public void checkStateChanged(CheckStateChangedEvent event) {
					checkPage();
				}
			});

			viewer.setLabelProvider(new GitLabelProvider());
			viewer.setInput(nodes);
			return area;
		}

		private void checkPage() {
			getButton(OK).setEnabled(
					fTree.getCheckboxTreeViewer().getCheckedLeafCount() > 0);

		}

		@Override
		protected void buttonPressed(int buttonId) {
			if (buttonId == IDialogConstants.OK_ID) {
				Object[] checked = fTree.getCheckboxTreeViewer()
						.getCheckedElements();
				for (Object o : checked) {
					if (o instanceof Ref)
						selected.add((Ref) o);
				}
			}
			super.buttonPressed(buttonId);
		}

		List<Ref> getSelected() {
			return selected;
		}

	}

