
		IWorkbenchSiteProgressService service = (IWorkbenchSiteProgressService) getSite()
				.getService(IWorkbenchSiteProgressService.class);
		if (service != null) {
			service.showBusyForFamily(JobFamilies.REPO_VIEW_REFRESH);
			service.showBusyForFamily(JobFamilies.CLONE);
		}
