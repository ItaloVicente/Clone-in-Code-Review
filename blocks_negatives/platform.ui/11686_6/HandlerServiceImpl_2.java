			Boolean result = ((Boolean) ContextInjectionFactory.invoke(handler, CanExecute.class,
					executionContext, staticContext, Boolean.TRUE));
			staticContext.set(CAN_EXECUTE, result);
			return result.booleanValue();
		} catch (Exception e) {
			if (Command.DEBUG_HANDLERS && logger != null) {
				StringBuilder message = new StringBuilder();
				logger.trace(e, message.toString());
			}
			return false;
