				public Expression getExpression() {
					return null;
				}

				public boolean evaluate(IEvaluationContext context) {
					return false;
				}

				public void clearResult() {
				}

				public boolean isActive(IEvaluationContext context) {
					return false;
				}

				public IHandlerService getHandlerService() {
					return (IHandlerService) wb.getService(IHandlerService.class);
				}

				public IHandler getHandler() {
					return null;
				}

				public int getDepth() {
					return 0;
				}

				public String getCommandId() {
					return cmdId;
				}

				public void clearActive() {
				}
			};
		}
		return systemHandlerActivation;
	}
