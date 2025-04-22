				if (ContextInjectionFactory.invoke(contribution,
						AboutToShow.class, parentContext, dynamicMenuContext,
						this) == this) {
					if (logger != null) {
						logger.error("Missing @AboutToShow method in " + contribution); //$NON-NLS-1$
					}
					continue;
				}
