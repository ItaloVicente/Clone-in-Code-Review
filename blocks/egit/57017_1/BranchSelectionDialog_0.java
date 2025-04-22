	private Set<Ref> getLocalBranches(List<T> list) {
		Set<Ref> branches = new HashSet<Ref>();
		for (Object o : list) {
			if (o instanceof Ref) {
				Ref r = (Ref) o;
				String name = r.getName();
				if (name.startsWith(Constants.R_HEADS)) {
					branches.add(r);
				}
			}
		}
		return branches;
	}

	private void preselectBranchMultiMode(List<T> list,
			FilteredCheckboxTree tree) {
		Set<Ref> branches = getLocalBranches(list);
		if (branches.size() == 1) {
			Ref b = branches.iterator().next();
			tree.getCheckboxTreeViewer().setChecked(b, true);
			preselectedBranch = true;
		}
	}

	private void preselectBranchSingleMode(List<T> list, TableViewer table) {
		Set<Ref> branches = getLocalBranches(list);
		if (branches.size() == 1) {
			Ref b = branches.iterator().next();
			table.setSelection(new StructuredSelection(b), true);
			preselectedBranch = true;
		}

	}

