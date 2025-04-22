		Object object = staticContext.get(HandlerServiceImpl.HANDLER_EXCEPTION);
		if (object instanceof ExecutionException) {
			if (logger != null) {
				logger.error((Throwable) object, "Command '" + cmd.getId() + "' failed"); //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
