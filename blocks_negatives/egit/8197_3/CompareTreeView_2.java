		if (baseVersion == null) {
			tree.setContentProvider(new WorkbenchTreeContentProvider());
			tree.setComparator(new WorkbenchTreeComparator());
			tree.setLabelProvider(new WorkbenchTreeLabelProvider());
		} else {
			tree.setContentProvider(new PathNodeContentProvider());
			tree.setComparator(new PathNodeTreeComparator());
			tree.setLabelProvider(new PathNodeLabelProvider());
		}
