	@Override
	protected String refNameFromDialog() {
		selectedRefs.clear();
		Set<String> selected = new HashSet<String>();
		IStructuredSelection selection = (IStructuredSelection) branchTree.getSelection();
		for (Object sel : selection.toArray()) {
			if (!(sel instanceof RefNode))
				continue;

			RefNode node = (RefNode) sel;
			Ref ref = node.getObject();
			selectedRefs.add(ref);
			selected.add(ref.getName());
		}

		boolean enabled = !selected.isEmpty()
				&& !selected.contains(currentBranch);
		getButton(Window.OK).setEnabled(enabled);

		return null;
	}

