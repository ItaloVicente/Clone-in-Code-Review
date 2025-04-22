	private static void updateCommandEnablement(final IEclipseContext evalContext, MMenuElement element,
			ParameterizedCommand cmd) {
		EHandlerService handlerService = evalContext.get(EHandlerService.class);
		if (cmd != null && handlerService != null) {
			MMenuItem item = (MMenuItem) element;
			final IEclipseContext staticContext = EclipseContextFactory.create(MMRF_STATIC_CONTEXT);
			ContributionsAnalyzer.populateModelInterfaces(item, staticContext,
					item.getClass().getInterfaces());
			try {
				item.setEnabled(handlerService.canExecute(cmd, staticContext));
			} finally {
				staticContext.dispose();
			}
		}
	}

	private static ParameterizedCommand getCommand(Object contrib) {
		if (!(contrib instanceof ContributionItem) || contrib instanceof ActionContributionItem) {
			return null;
		}
		Class<? extends Object> clazz = contrib.getClass();
		if (!clazz.getName().equals("org.eclipse.ui.menus.CommandContributionItem")) { //$NON-NLS-1$
			return null;
		}
		try {
			Object cmd = clazz.getMethod("getCommand").invoke(contrib); //$NON-NLS-1$
			if (cmd instanceof ParameterizedCommand) {
				return ParameterizedCommand.class.cast(cmd);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			return null;
		}
		return null;
	}

