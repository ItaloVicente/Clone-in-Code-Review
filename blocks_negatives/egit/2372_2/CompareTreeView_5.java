		if (leftVersion == null) {
			leftTree
					.setContentProvider(new LocalWorkbenchTreeContentProvider());
			leftTree.setLabelProvider(new AddingWorkbenchLabelProvider());

			rightTree
					.setContentProvider(new RepositoryWorkbenchTreeContentProvider());
