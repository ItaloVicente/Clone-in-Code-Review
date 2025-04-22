
	public void testEvaluateNeedsToCheckActiveWhenBeforeHandlerEnablement() {
		HandlerThrowingExceptionInSetEnabled handler = new HandlerThrowingExceptionInSetEnabled();
		Expression activeWhen = Expression.FALSE; // always false
		IHandlerActivation handlerActivation = handlerService.activateHandler(CMD_ID, handler, activeWhen);
		testHandlerActivations.put(handler, handlerActivation);

		handlerActivation.evaluate(new EvaluationContext(null, new Object()));

		assertFalse(handler.setEnabledWasCalled);
	}

	public void testEvaluateShouldSwallowExceptionFromHandlerEnablementCheck() {
		HandlerThrowingExceptionInSetEnabled handler = new HandlerThrowingExceptionInSetEnabled();
		Expression activeWhen = Expression.TRUE; // always true
		IHandlerActivation handlerActivation = handlerService.activateHandler(CMD_ID, handler, activeWhen);
		testHandlerActivations.put(handler, handlerActivation);

		handlerActivation.evaluate(new EvaluationContext(null, new Object()));

		assertTrue(handler.setEnabledWasCalled);

	}

