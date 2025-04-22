			PreviousCommit previous = findPreviousCommit();
			if (previous != null)
				if (resource instanceof IFile) {
					final ITypedElement base = SaveableCompareEditorInput
							.createFileElement((IFile) resource);
					ITypedElement next = CompareUtils
							.getFileRevisionTypedElement(previous.path,
									previous.commit, repository);
					CompareEditorInput input = new GitCompareFileRevisionEditorInput(
							base, next, null);
					CompareUI.openCompareEditor(input);
				} else
					openCompareTreeView(previous.commit);
			else
