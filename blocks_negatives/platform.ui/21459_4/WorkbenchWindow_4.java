			public boolean save(MPart dirtyPart, boolean confirm) {
				Object object = dirtyPart.getObject();
				if (object instanceof CompatibilityPart) {
					IWorkbenchPart workbenchPart = ((CompatibilityPart) object).getPart();
					if (workbenchPart instanceof ISaveablePart) {
						ISaveablePart saveablePart = (ISaveablePart) workbenchPart;
						return page.saveSaveable(saveablePart, workbenchPart, confirm, true);
