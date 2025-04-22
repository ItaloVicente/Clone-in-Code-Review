
			public boolean save(MPart dirtyPart, boolean confirm) {
				Object object = dirtyPart.getObject();
				if (object instanceof CompatibilityPart) {
					IWorkbenchPart workbenchPart = ((CompatibilityPart) object).getPart();
					if (workbenchPart instanceof ISaveablePart) {
						ISaveablePart saveablePart = (ISaveablePart) workbenchPart;
						return page.saveSaveable(saveablePart, workbenchPart, confirm, false);
					}
				}
				return super.save(dirtyPart, confirm);
			}

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
				return WorkbenchPage.saveAll(saveables, confirm, false, true, WorkbenchWindow.this,
						WorkbenchWindow.this);
			}
		};
		localSaveHandler.logger = logger;
		windowContext.set(ISaveHandler.class, localSaveHandler);
