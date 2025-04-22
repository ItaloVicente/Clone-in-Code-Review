		graph.getTableView().addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				final Object input = getInput();
				if (!(input instanceof IFile)) {
					return;
				}
				final IFile resource = (IFile) input;
				final RepositoryMapping mapping = RepositoryMapping.getMapping(resource.getProject());
				final String gitPath = mapping.getRepoRelativePath(resource);
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				SWTCommit commit = (SWTCommit) selection.getFirstElement();
				System.out.println(selection);
				final IFileRevision nextFile = GitFileRevision.inCommit(
						db,
						commit,
						gitPath,
						null);
				final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						SaveableCompareEditorInput.createFileElement(resource),
						new EditableRevision(nextFile),
						null);
				openInCompare(in);
			}
		});
