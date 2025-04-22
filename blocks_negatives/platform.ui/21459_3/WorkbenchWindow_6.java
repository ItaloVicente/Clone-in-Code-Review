				if (saveables.isEmpty()) {
					return super.saveParts(dirtyParts, confirm);
				}
				return WorkbenchPage.saveAll(saveables, confirm, false, true, WorkbenchWindow.this,
						WorkbenchWindow.this);
			}
		};
		localSaveHandler.logger = logger;
		windowContext.set(ISaveHandler.class, localSaveHandler);

		windowContext.set(IWorkbenchWindow.class.getName(), this);
		windowContext.set(IPageService.class, this);
		windowContext.set(IPartService.class, partService);

		windowContext.set(ISources.ACTIVE_WORKBENCH_WINDOW_NAME, this);
		windowContext.set(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME, getShell());
		EContextService cs = (EContextService) windowContext.get(EContextService.class.getName());
		cs.activateContext(IContextService.CONTEXT_ID_WINDOW);
		cs.getActiveContextIds();
