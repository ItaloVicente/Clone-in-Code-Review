			} else if (clientObject != null) {
				if (part.getTransientData().get(E4PartWrapper.E4_WRAPPER_KEY) instanceof E4PartWrapper) {
					return (IWorkbenchPart) part.getTransientData().get(
							E4PartWrapper.E4_WRAPPER_KEY);
				}

				ViewReference viewReference = getViewReference(part);
				E4PartWrapper legacyPart = new E4PartWrapper(part);
				try {
					viewReference.initialize(legacyPart);
				} catch (PartInitException e) {
					WorkbenchPlugin.log(e);
				}
				part.getTransientData().put(E4PartWrapper.E4_WRAPPER_KEY, legacyPart);
				return legacyPart;
