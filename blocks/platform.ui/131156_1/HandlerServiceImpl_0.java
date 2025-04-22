			Object obj = staticContext.get(HandlerServiceImpl.HANDLER_EXCEPTION);
			if (obj instanceof ExecutionException) {
				if (logger != null) {
					logger.error((Throwable) obj, "Command '" + command.getId() + "' failed"); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
