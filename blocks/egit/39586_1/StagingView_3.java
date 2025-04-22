	private void schedule(Job job, boolean useRepositoryRule) {
		if (useRepositoryRule)
			job.setRule(RuleUtil.getRule(currentRepository));
		IWorkbenchSiteProgressService service = CommonUtils.getService(getSite(), IWorkbenchSiteProgressService.class);
		if (service != null)
			service.schedule(job, 0, true);
		else
			job.schedule();
	}

