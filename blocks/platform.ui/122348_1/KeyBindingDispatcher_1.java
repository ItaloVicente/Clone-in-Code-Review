						if (handlerService instanceof HandlerServiceImpl) {
							HandlerServiceImpl serviceImpl = (HandlerServiceImpl) handlerService;
							IEclipseContext serviceContext = serviceImpl.getContext();
							if (serviceContext != null) {
								StringBuilder sb = new StringBuilder("\n\tExecution context: "); //$NON-NLS-1$
								sb.append(describe(serviceContext));
								logger.trace(sb.toString());
							}
						}
