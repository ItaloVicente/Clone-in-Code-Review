		staticContext.remove(NOT_HANDLED);

		Object rc = ContextInjectionFactory.invoke(handler, CanExecute.class, executionContext,
				staticContext, Boolean.TRUE);
		staticContext.set(CAN_EXECUTE, rc);
		if (Boolean.FALSE.equals(rc))
			return null;
		return ContextInjectionFactory.invoke(handler, Execute.class, executionContext,
				staticContext, null);
