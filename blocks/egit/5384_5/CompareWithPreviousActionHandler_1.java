				return;
			} else if (previousList.size() > 1)
				HandlerUtil.getActiveShell(event).getDisplay()
						.syncExec(new Runnable() {
							public void run() {
								CommitSelectDialog dlg = new CommitSelectDialog(
										HandlerUtil.getActiveShell(event),
										previousList);
								if (dlg.open() == Window.OK)
									previous.set(dlg.getSelectedCommit());
								else
									throw new OperationCanceledException();
							}
						});
			else
				previous.set(previousList.get(0));

			if (resource instanceof IFile) {
				final ITypedElement base = SaveableCompareEditorInput
						.createFileElement((IFile) resource);
				ITypedElement next = CompareUtils.getFileRevisionTypedElement(
						getRepositoryPath(), previous.get(), repository);
				CompareEditorInput input = new GitCompareFileRevisionEditorInput(
						base, next, null);
				CompareUI.openCompareEditor(input);
			} else
				openCompareTreeView(previous.get());
