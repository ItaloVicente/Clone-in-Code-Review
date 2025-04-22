	private void addCopyAction(final TreeViewer viewer) {
		IAction copyAction = createSelectionPathCopyAction(viewer);

		ActionUtils.setGlobalActions(viewer.getControl(),
				getSite().getService(IHandlerService.class), copyAction);
	}

	private IAction createSelectionPathCopyAction(final TreeViewer viewer) {
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		String copyPathActionText = (selection.size() <= 1) ? UIText.StagingView_CopyPath
						: UIText.StagingView_CopyPaths;
		IAction copyAction = ActionUtils.createGlobalAction(ActionFactory.COPY,
				() -> copyPathOfSelectionToClipboard(viewer));
		copyAction.setText(copyPathActionText);
		return copyAction;
	}

	private void copyPathOfSelectionToClipboard(final TreeViewer viewer) {
		Clipboard cb = new Clipboard(viewer.getControl().getDisplay());
		TextTransfer t = TextTransfer.getInstance();
		String text = getTextFrom(
				(IStructuredSelection) viewer.getSelection());
		try {
			if (text != null) {
				cb.setContents(new Object[] { text }, new Transfer[] { t });
			}
		} finally {
			cb.dispose();
		}
	}

	@Nullable
	private String getTextFrom(IStructuredSelection selection) {
		Object[] selectionEntries = selection.toArray();
		if (selectionEntries.length <= 0) {
			return null;
		} else if (selectionEntries.length == 1) {
			return getPathFrom(selectionEntries[0]);
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < selectionEntries.length; i++) {
				String text = getPathFrom(selectionEntries[i]);
				if (text != null) {
					if (i < selectionEntries.length - 1) {
						sb.append(text).append(System.lineSeparator());
					} else {
						sb.append(text);
					}
				}
			}
			return sb.toString();
		}
	}

	@Nullable
	private String getPathFrom(Object obj) {
		if (obj instanceof StagingEntry) {
			return ((StagingEntry) obj).getPath();
		} else if (obj instanceof StagingFolderEntry) {
			return ((StagingFolderEntry) obj).getPath().toString();
		}
		return null;
	}

