	private void doCopy() {
		final ISelection s = getSelection();
		if (s.isEmpty() || !(s instanceof IStructuredSelection))
			return;
		final IStructuredSelection iss = (IStructuredSelection) s;
		final Iterator<FileDiff> itr = iss.iterator();
