		graph.getTableView().addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if(selection instanceof IStructuredSelection) {
					IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					Object commit = structuredSelection.getFirstElement();
					if(commit != null && commit instanceof SWTCommit) {
						SWTCommit swtCommit = (SWTCommit) commit;
						CommitEditor.openQuiet(new RepositoryCommit(currentRepo, swtCommit));
					}
				}
			}
		});
