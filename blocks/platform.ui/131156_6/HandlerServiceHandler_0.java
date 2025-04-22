		} catch (InjectionException e) {
			if (e.getCause() instanceof ExecutionException) {
				ExecutionException executionException = (ExecutionException) e.getCause();
				throw executionException;
			}
			String message = "Error executing '" + commandId + "': " + e.getMessage(); //$NON-NLS-1$ //$NON-NLS-2$
			throw new ExecutionException(message, e);
