		FormatJob.FormatRequest formatRequest = new FormatJob.FormatRequest(db,
				commit, fill, currentDiffs, SYS_LINKCOLOR, SYS_DARKGRAY,
				SYS_HUNKHEADER_COLOR, SYS_LINES_ADDED_COLOR,
				SYS_LINES_REMOVED_COLOR);
		formatJob = new FormatJob(formatRequest);
		addDoneListenerToFormatJob();
		siteService.schedule(formatJob, 0 /* now */, true /*
