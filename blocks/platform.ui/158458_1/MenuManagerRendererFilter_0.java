			} else if (updateEnablement && OpaqueElementUtil.isOpaqueMenuItem(element)) {
				Object obj = OpaqueElementUtil.getOpaqueItem(element);
				if (obj instanceof CommandContributionItem) {
					CommandContributionItem contribution = (CommandContributionItem) obj;
					ParameterizedCommand cmd = contribution.getCommand();

					EHandlerService handlerService = evalContext.get(EHandlerService.class);
					if (cmd != null && handlerService != null) {
						MDirectMenuItem item = (MDirectMenuItem) element;
						final IEclipseContext staticContext = EclipseContextFactory.create(MMRF_STATIC_CONTEXT);
						ContributionsAnalyzer.populateModelInterfaces(item, staticContext,
								item.getClass().getInterfaces());
						try {
							((MHandledMenuItem) element).setEnabled(handlerService.canExecute(cmd, staticContext));
						} finally {
							staticContext.dispose();
						}
					}
				}
