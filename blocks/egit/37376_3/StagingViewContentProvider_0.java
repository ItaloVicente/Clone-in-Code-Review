		Job submoduleWalkJob = new SubmoduleWalkJob(
				UIText.StagingViewContentProvider_WalkSubmodules, repository,
				nodes, stagingView);
		IWorkbenchSiteProgressService service = CommonUtils.getService(
				stagingView.getSite(), IWorkbenchSiteProgressService.class);
		if (service != null)
			service.schedule(submoduleWalkJob, 0, true);
		else
			submoduleWalkJob.schedule();
