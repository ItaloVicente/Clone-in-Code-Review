		IServiceLocator serviceLocator = HandlerUtil.getActiveSite(event);
		IWorkbenchSiteProgressService service = null;
		if (serviceLocator != null) {
			service = CommonUtils.getService(serviceLocator,
					IWorkbenchSiteProgressService.class);
		}
