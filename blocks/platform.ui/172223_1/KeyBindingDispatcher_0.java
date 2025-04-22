				} else if (isTracingEnabled()) {
					logger.trace((Throwable) commandException,
							"Command exception for: " + parameterizedCommand + " in " //$NON-NLS-1$ //$NON-NLS-2$
									+ describe(context));
					if (handlerService instanceof HandlerServiceImpl) {
						HandlerServiceImpl serviceImpl = (HandlerServiceImpl) handlerService;
						IEclipseContext serviceContext = serviceImpl.getContext();
						if (serviceContext != null) {
							StringBuilder sb = new StringBuilder("\n\tExecution context: "); //$NON-NLS-1$
							sb.append(describe(serviceContext));
							sb.append("\n\tHandler: "); //$NON-NLS-1$
							sb.append(obj);
							logger.trace(sb.toString());
