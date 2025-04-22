
		final MenuManager mgr = new MenuManager();
		Control c = getControl();
		c.setMenu(mgr.createContextMenu(c));

		open = new Action(UIText.CommitFileDiffViewer_OpenInEditorMenuLabel) {
			@SuppressWarnings("unchecked")
			@Override
			public void run() {
				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				for (Iterator<FileDiff> it = iss.iterator();; it.hasNext()) {
					openFileInEditor(it.next());
				}
			}
		};

		compare = new Action(UIText.CommitFileDiffViewer_CompareMenuLabel) {
			@Override
			public void run() {
				final ISelection s = getSelection();
				if (s.isEmpty() || !(s instanceof IStructuredSelection))
					return;
				final IStructuredSelection iss = (IStructuredSelection) s;
				final FileDiff d = (FileDiff) iss.getFirstElement();
				if (d.getBlobs().length <= 2)
					showTwoWayFileDiff(d);
				else
					MessageDialog
							.openInformation(
									PlatformUI.getWorkbench()
											.getActiveWorkbenchWindow()
											.getShell(),
									UIText.CommitFileDiffViewer_CanNotOpenCompareEditorTitle,
									UIText.CommitFileDiffViewer_MergeCommitMultiAncestorMessage);
			}
		};

		mgr.add(open);
		mgr.add(compare);

		mgr.add(new Separator());
		mgr.add(selectAll = createStandardAction(ActionFactory.SELECT_ALL));
		mgr.add(copy = createStandardAction(ActionFactory.COPY));

		getControl().addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent e) {
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.SELECT_ALL.getId(), null);
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.COPY.getId(), null);
				site.getActionBars().updateActionBars();
			}

			public void focusGained(FocusEvent e) {
				updateActionEnablement(getSelection());
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.SELECT_ALL.getId(), selectAll);
				site.getActionBars().setGlobalActionHandler(
						ActionFactory.COPY.getId(), copy);
				site.getActionBars().updateActionBars();
			}
		});
	}

	private void updateActionEnablement(ISelection selection) {
		if (!(selection instanceof IStructuredSelection))
			return;
		IStructuredSelection sel = (IStructuredSelection) selection;
		boolean allSelected = !sel.isEmpty()
				&& sel.size() == getTable().getItemCount();
		selectAll.setEnabled(!allSelected);
		copy.setEnabled(!sel.isEmpty());
		open.setEnabled(!sel.isEmpty());
		compare.setEnabled(sel.size() == 1);
	}

	private IAction createStandardAction(final ActionFactory af) {
		final String text = af.create(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()).getText();
		IAction action = new Action() {

			@Override
			public String getActionDefinitionId() {
				return af.getCommandId();
			}

			@Override
			public String getId() {
				return af.getId();
			}

			@Override
			public String getText() {
				return text;
			}

			@Override
			public void run() {
				if (af == ActionFactory.SELECT_ALL) {
					doSelectAll();
				}
				if (af == ActionFactory.COPY) {
					doCopy();
				}
			}
		};
		action.setEnabled(true);
		return action;
