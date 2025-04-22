		initialBranch.setInput(branches);
		if (head != null && branches.contains(head))
			initialBranch.setSelection(new StructuredSelection(head));
		else
			initialBranch
					.setSelection(new StructuredSelection(branches.get(0)));
