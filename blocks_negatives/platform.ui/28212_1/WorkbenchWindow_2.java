		final ISaveHandler defaultSaveHandler = windowContext.get(ISaveHandler.class);
		final PartServiceSaveHandler localSaveHandler = new PartServiceSaveHandler() {
			public Save promptToSave(MPart dirtyPart) {
				Object object = dirtyPart.getObject();
				if (object instanceof CompatibilityPart) {
					IWorkbenchPart part = ((CompatibilityPart) object).getPart();
					if (part instanceof ISaveablePart) {
						if (!((ISaveablePart) part).isSaveOnCloseNeeded())
							return Save.NO;
						return SaveableHelper.savePart((ISaveablePart) part, part,
								WorkbenchWindow.this, true) ? Save.NO : Save.CANCEL;
					}
