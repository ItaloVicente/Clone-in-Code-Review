			Object result = ContextInjectionFactory.invoke(handler, Execute.class,
					executionContext, staticContext, this);
			if (result == this) {
				throw new ExecutionException(
						"Handler missing @Execute: " + handler.getClass().getName() /* HANDLER_MISSING_EXECUTE_ANNOTATION */); //$NON-NLS-1$
			}
			return result;
