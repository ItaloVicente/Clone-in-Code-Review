	protected IEGitOperation createOperation(List<RevCommit> selection) {
		return new QuickdiffBaselineOperation(getActiveRepository(), selection.get(0).getId().name());
	}

	@Override
	protected String getJobName() {
		return UIText.SetQuickdiffBaselineAction_setQuickdiffBaseline;
