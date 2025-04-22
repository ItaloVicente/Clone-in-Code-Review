	static class HandlerThrowingExceptionInSetEnabled extends AbstractHandler {

		public boolean setEnabledWasCalled;

		@Override
		public Object execute(ExecutionEvent event) {
			return null;
		}

		@Override
		public void setEnabled(Object evaluationContext) {
			setEnabledWasCalled = true;
			throw new RuntimeException("simulated exception");
		}
	}

