			final Object obj = staticContext.get(HandlerServiceImpl.HANDLER_EXCEPTION);
			if (obj instanceof ExecutionException) {
				throw (ExecutionException) obj;
			} else if (obj instanceof NotDefinedException) {
				throw (NotDefinedException) obj;
			} else if (obj instanceof NotEnabledException) {
				throw (NotEnabledException) obj;
			} else if (obj instanceof NotHandledException) {
				throw (NotHandledException) obj;
			} else if (obj instanceof Exception) {
				WorkbenchPlugin.log((Exception) obj);
