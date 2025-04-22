		} catch (InjectionException e) {
			if (e.getCause() instanceof ExecutionException) {
				ExecutionException executionException = (ExecutionException) e.getCause();
				throw executionException;
			}
			throw new ExecutionException("Error invoking " + handler + " in " + staticContext, e); //$NON-NLS-1$ //$NON-NLS-2$
