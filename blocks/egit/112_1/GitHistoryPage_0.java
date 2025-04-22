		graph.getTableView().addOpenListener(new IOpenListener() {
			public void open(OpenEvent event) {
				final IResource resource = (IResource) getInput();
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
				final IFile baseFile = (IFile) resource;
				final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						SaveableCompareEditorInput.createFileElement(baseFile),
						new EditableRevision(nextFile),
						null);
				openInCompare(in);
			}
		});
