		}
		RevCommit revCommit = AdapterUtils.adapt(selection.getFirstElement(),
				RevCommit.class);
		if (!(revCommit instanceof PlotCommit)) {
			return branchesOfCommit;
		}
		PlotCommit commit = (PlotCommit) revCommit;
