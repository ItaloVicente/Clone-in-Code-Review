		final RefSpec spec = new RefSpec().setSource(refText.getText())
				.setDestination(Constants.FETCH_HEAD);
		final String uri = uriCombo.getText();
		final boolean doCheckout = checkout.getSelection();
		final boolean doCreateTag = createTag.getSelection();
		final boolean doCreateBranch = createBranch.getSelection();
		final boolean doActivateAdditionalRefs = (checkout.getSelection() || dontCheckout
				.getSelection()) && activateAdditionalRefs.getSelection();
		final String textForTag = tagText.getText();
		final String textForBranch = branchText.getText();

		Job job = new Job(UIText.FetchGerritChangePage_GetChangeTaskName) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				internalDoFetch(spec, uri, doCheckout, doCreateTag,
						doCreateBranch, doActivateAdditionalRefs, textForTag,
						textForBranch, monitor);
				return org.eclipse.core.runtime.Status.OK_STATUS;
			}
