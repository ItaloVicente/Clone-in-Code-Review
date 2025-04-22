		Object result = ContextInjectionFactory.invoke(contrib.getObject(),
				Execute.class, getExecutionContext(lclContext), staticContext,
				this);
		if (result == this && logger != null) {
			logger.error("Contribution is missing @Execute: " + contrib.getContributionURI()); //$NON-NLS-1$
		}
