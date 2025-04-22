		if (!(page.getInput() instanceof IFile))
			return false;
		IStructuredSelection sel = getSelection(page);
		Object[] selected = sel.toArray();
		return selected.length == 2 && selected[0] instanceof RevCommit
				&& selected[1] instanceof RevCommit;
