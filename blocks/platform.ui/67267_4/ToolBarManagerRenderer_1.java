	@PreDestroy
	void preDestroy() {
		if (logger.isDebugEnabled()) {
			logger.debug("\nTBMR:dispose: modelToManager size = {0}, managerToModel size = {1}", //$NON-NLS-1$
					modelToManager.size(), managerToModel.size());
		}
	}

