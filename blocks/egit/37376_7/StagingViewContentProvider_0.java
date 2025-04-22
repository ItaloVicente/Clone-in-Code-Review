		Job submoduleWalkJob = new SubmoduleWalkJob(
				UIText.StagingViewContentProvider_WalkSubmodules, repository,
				nodes, stagingView, this);
		IWorkbenchSiteProgressService service = CommonUtils.getService(
				stagingView.getSite(), IWorkbenchSiteProgressService.class);
		if (service != null)
			service.schedule(submoduleWalkJob, 50, true);
		else
			submoduleWalkJob.schedule(50);
