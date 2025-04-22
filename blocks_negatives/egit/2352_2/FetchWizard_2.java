		transport.setTagOpt(refSpecPage.getTagOpt());

		final Job fetchJob = new FetchJob(transport, refSpecPage.getRefSpecs(),
				getSourceString());
		fetchJob.setUser(true);
		fetchJob.schedule();
