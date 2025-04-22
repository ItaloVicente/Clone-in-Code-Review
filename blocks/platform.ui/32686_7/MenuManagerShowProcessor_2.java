					IEclipseContext dynamicMenuContext = EclipseContextFactory
							.create();
					ArrayList<MMenuElement> mel = new ArrayList<MMenuElement>();
					dynamicMenuContext.set(List.class, mel);
					IEclipseContext parentContext = modelService
							.getContainingContext(currentMenuElement);
					Object rc = ContextInjectionFactory.invoke(contribution,
							AboutToShow.class, parentContext,
							dynamicMenuContext, this);
					dynamicMenuContext.dispose();
					if (rc == this) {
						if (logger != null) {
							logger.error("Missing @AboutToShow method in " + contribution); //$NON-NLS-1$
						}
						continue;
