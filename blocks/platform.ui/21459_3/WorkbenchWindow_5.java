				public boolean saveParts(Collection<MPart> dirtyParts, boolean confirm) {
					ArrayList<ISaveablePart> saveables = new ArrayList<ISaveablePart>();
					for (MPart part : dirtyParts) {
						Object object = part.getObject();
						if (object instanceof CompatibilityPart) {
							IWorkbenchPart workbenchPart = ((CompatibilityPart) object).getPart();
							if (workbenchPart instanceof ISaveablePart) {
								saveables.add((ISaveablePart) workbenchPart);
							}
						}
					}
					if (saveables.isEmpty()) {
						return super.saveParts(dirtyParts, confirm);
					}
					return WorkbenchPage.saveAll(saveables, confirm, false, true,
							WorkbenchWindow.this, WorkbenchWindow.this);
				}
			};
			localSaveHandler.logger = logger;
			windowContext.set(ISaveHandler.class, localSaveHandler);

			windowContext.set(IWorkbenchWindow.class.getName(), this);
			windowContext.set(IPageService.class, this);
			windowContext.set(IPartService.class, partService);

			windowContext.set(ISources.ACTIVE_WORKBENCH_WINDOW_NAME, this);
			windowContext.set(ISources.ACTIVE_WORKBENCH_WINDOW_SHELL_NAME, getShell());
			EContextService cs = (EContextService) windowContext.get(EContextService.class
					.getName());
			cs.activateContext(IContextService.CONTEXT_ID_WINDOW);
			cs.getActiveContextIds();

			initializeDefaultServices();

			cleanLegacyQuickAccessContribution();


			fireWindowOpening();
			configureShell(getShell(), windowContext);
