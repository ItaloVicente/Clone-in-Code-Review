	@PreDestroy
	void preDestroy() {
		if (logger.isDebugEnabled()) {
			System.out.printf("TBMR:dispose: modelToManager size = %d, managerToModel size = %d\n", //$NON-NLS-1$
					modelToManager.size(), managerToModel.size());
		}
	}

