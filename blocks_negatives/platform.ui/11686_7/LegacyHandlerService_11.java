			if (staticContext.get(HandlerServiceImpl.NOT_HANDLED) == Boolean.TRUE) {
				final NotHandledException e = new NotHandledException(
				CommandProxy.fireNotHandled(command.getCommand(), e);
				throw e;
			}
			final Object obj = staticContext.get(HandlerServiceImpl.CAN_EXECUTE);
			if (obj instanceof Boolean) {
				if (!((Boolean) obj).booleanValue()) {
					final NotEnabledException exception = new NotEnabledException(
					CommandProxy.fireNotEnabled(command.getCommand(), exception);
					throw exception;
				}
