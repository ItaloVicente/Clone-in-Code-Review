				try {
					ContextInjectionFactory.invoke(contribution,
							AboutToShow.class, dynamicMenuContext);
				} catch (InjectionException injex) {
					if (logger != null
							&& missingAboutToShowMethod(contribution)) {
						logger.error("Missing @AboutToShow method in " + contribution); //$NON-NLS-1$
					} else {
						throw injex;
					}
				}
