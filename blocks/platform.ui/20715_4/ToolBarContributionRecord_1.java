	public void collectInfo(ExpressionInfo info) {
		ContributionsAnalyzer.collectInfo(info,
				toolbarContribution.getVisibleWhen());
		for (MToolBarElement item : generatedElements) {
			ContributionsAnalyzer.collectInfo(info, item.getVisibleWhen());
		}
		for (MToolBarElement item : sharedElements) {
			ContributionsAnalyzer.collectInfo(info, item.getVisibleWhen());
		}
	}

