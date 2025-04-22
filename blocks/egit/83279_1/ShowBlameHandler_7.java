			if (revision instanceof CommitFileRevision) {
				BlameOperation op = new BlameOperation(
						(CommitFileRevision) revision,
						HandlerUtil.getActiveShell(event),
						page.getSite().getPage());
				JobUtil.scheduleUserJob(op, UIText.ShowBlameHandler_JobName,
						JobFamilies.BLAME);
