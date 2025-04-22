			RevCommit previous = findPreviousCommit();
			if (previous != null)
				if (resource instanceof IFile) {
					final ITypedElement base = SaveableCompareEditorInput
							.createFileElement((IFile) resource);
					ITypedElement next = CompareUtils
							.getFileRevisionTypedElement(getRepositoryPath(),
									previous, repository);
					CompareEditorInput input = new GitCompareFileRevisionEditorInput(
							base, next, null);
					CompareUI.openCompareEditor(input);
				} else
					openCompareTreeView(previous);
			else
