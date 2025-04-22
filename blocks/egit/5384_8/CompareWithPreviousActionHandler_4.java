				return;
			} else if (previousList.size() > 1){
				final List<RevCommit> commits = new ArrayList<RevCommit>();
				for (PreviousCommit pc: previousList)
					commits.add(pc.commit);
				HandlerUtil.getActiveShell(event).getDisplay()
						.syncExec(new Runnable() {
							public void run() {
								CommitSelectDialog dlg = new CommitSelectDialog(
										HandlerUtil.getActiveShell(event),
										commits);
								if (dlg.open() == Window.OK)
									for (PreviousCommit pc: previousList)
										if (pc.commit.equals(dlg.getSelectedCommit())){
											   previous.set(pc);
											   break;
										   }
							}
						});
			}
			else
				previous.set(previousList.get(0));

			if (previous.get() == null)
				return;
			if (resource instanceof IFile) {
				final ITypedElement base = SaveableCompareEditorInput
						.createFileElement((IFile) resource);
				PreviousCommit pc = previous.get();
				ITypedElement next = CompareUtils.getFileRevisionTypedElement(
						pc.path, pc.commit, repository);
				CompareEditorInput input = new GitCompareFileRevisionEditorInput(
						base, next, null);
				CompareUI.openCompareEditor(input);
			} else
				openCompareTreeView(previous.get().commit);
