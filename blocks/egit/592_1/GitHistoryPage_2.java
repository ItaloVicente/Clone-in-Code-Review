				if (compareMode) {
					final IFile resource = (IFile) input;
					final RepositoryMapping mapping = RepositoryMapping.getMapping(resource.getProject());
					final String gitPath = mapping.getRepoRelativePath(resource);
					IStructuredSelection selection = (IStructuredSelection) event.getSelection();
					SWTCommit commit = (SWTCommit) selection.getFirstElement();
					ITypedElement right = getFileRevisionTypedElement(resource, gitPath, commit);
					final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
							SaveableCompareEditorInput.createFileElement(resource),
							right,
							null);
					openInCompare(in);
				} else {
					new ViewVersionsAction().run();
				}
