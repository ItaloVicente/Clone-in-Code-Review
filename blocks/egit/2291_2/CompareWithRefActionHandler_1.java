			if (resource instanceof IFile) {
				final IFile baseFile = (IFile) resource;

				final ITypedElement base = new FileRevisionTypedElement(
						new LocalFileRevision(baseFile));

				final ITypedElement next;
				try {
					next = getElementForRef(mapping.getRepository(), mapping
							.getRepoRelativePath(baseFile), dlg.getRefName());
				} catch (IOException e) {
					Activator.handleError(
							UIText.CompareWithIndexAction_errorOnAddToIndex, e,
							true);
					return null;
				}

				final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						base, next, null);
				in.getCompareConfiguration().setRightLabel(dlg.getRefName());
				CompareUI.openCompareEditor(in);
