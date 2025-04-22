	private void doCheckout(BranchOperation bop, boolean restore,
			IProgressMonitor monitor)
			throws CoreException {
		SubMonitor progress = SubMonitor.convert(monitor, restore ? 10 : 1);
		if (!restore) {
			bop.execute(progress.newChild(1));
		} else {
