			public void open(OpenEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection)
					for (Object element : ((IStructuredSelection) selection)
							.toArray())
						if (element instanceof RepositoryCommit)
							CommitEditor.openQuiet((RepositoryCommit) element);
