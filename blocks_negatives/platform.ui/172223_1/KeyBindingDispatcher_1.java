				} else {
					if (isTracingEnabled()) {
						logger.trace((Throwable) commandException,
										+ describe(context));
						if (handlerService instanceof HandlerServiceImpl) {
							HandlerServiceImpl serviceImpl = (HandlerServiceImpl) handlerService;
							IEclipseContext serviceContext = serviceImpl.getContext();
							if (serviceContext != null) {
								sb.append(describe(serviceContext));
								sb.append(obj);
								logger.trace(sb.toString());
							}
