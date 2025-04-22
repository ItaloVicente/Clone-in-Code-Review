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
				ITypedElement right = getEditableRevision(resource, gitPath,
						commit);
				final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						SaveableCompareEditorInput.createFileElement(resource),
						right,
						null);
				openInCompare(in);
			}

			private ITypedElement getEditableRevision(final IFile resource,
					final String gitPath, SWTCommit commit) {
				ITypedElement right = new EmptyElement(NLS.bind(UIText.GitHistoryPage_FileNotInCommit,
						resource.getName(), commit));

				try {
					TreeWalk w = TreeWalk.forPath(db, gitPath, commit.getTree());
					if (w != null) {
						final IFileRevision nextFile = GitFileRevision.inCommit(
								db,
								commit,
								gitPath,
								null);
						right = new EditableRevision(nextFile);
					}
				} catch (IOException e) {
					Activator.error("IO error looking up path" + gitPath + " in "
							+ commit.getId() + ".", e);
				}
				return right;
			}
		});
