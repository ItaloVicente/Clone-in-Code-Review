		IServiceLocator serviceLocator = HandlerUtil.getActiveSite(event);
		if (serviceLocator != null) {
			IWorkbenchSiteProgressService service = CommonUtils.getService(
					serviceLocator, IWorkbenchSiteProgressService.class);
			service.schedule(job);
		} else {
			job.schedule();
		}
