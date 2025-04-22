		RevCommit revCommit = AdapterUtils
				.adapt(getSelection(event).getFirstElement(), RevCommit.class);
		if (!(revCommit instanceof PlotCommit)) {
			return null;
		}
		PlotCommit commit = (PlotCommit) revCommit;
