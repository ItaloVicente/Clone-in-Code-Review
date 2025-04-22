			windowContext.set(ISources.ACTIVE_WORKBENCH_WINDOW_NAME, this);
			windowContext.set(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME, getShell());
			EContextService cs = (EContextService) windowContext.get(EContextService.class
					.getName());
			cs.activateContext(IContextService.CONTEXT_ID_WINDOW);
			cs.getActiveContextIds();
