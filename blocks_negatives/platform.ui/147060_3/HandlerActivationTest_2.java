
	public void testEvaluateNeedsToCheckActiveWhenBeforeHandlerEnablement() {
		HandlerThrowingExceptionInSetEnabled handler = new HandlerThrowingExceptionInSetEnabled();
		IHandlerActivation handlerActivation = handlerService.activateHandler(CMD_ID, handler, activeWhen);
		testHandlerActivations.put(handler, handlerActivation);

		handlerActivation.evaluate(new EvaluationContext(null, new Object()));

		assertFalse(handler.setEnabledWasCalled);
	}

	public void testEvaluateShouldSwallowExceptionFromHandlerEnablementCheck() {
		HandlerThrowingExceptionInSetEnabled handler = new HandlerThrowingExceptionInSetEnabled();
		IHandlerActivation handlerActivation = handlerService.activateHandler(CMD_ID, handler, activeWhen);
		testHandlerActivations.put(handler, handlerActivation);

		handlerActivation.evaluate(new EvaluationContext(null, new Object()));

		assertTrue(handler.setEnabledWasCalled);

	}

