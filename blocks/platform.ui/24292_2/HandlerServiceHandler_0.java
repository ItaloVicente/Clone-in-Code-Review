			Object result = ContextInjectionFactory.invoke(handler, Execute.class,
 executionContext,
					staticContext, missingExecute);
			if (result == missingExecute) {
				throw new ExecutionException(HANDLER_MISSING_EXECUTE_ANNOTATION,
						new NotHandledException(getClass().getName()));
			}
			return result;
