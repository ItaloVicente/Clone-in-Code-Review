		if (serviceLocator == null)
			return null;

		IHandlerService handlerService = CommonUtils.getService(serviceLocator, IHandlerService.class);
		if (handlerService == null)
			return null;

		IEvaluationContext evaluationContext = handlerService.getCurrentState();
		return SelectionUtils.getRepository(evaluationContext);
