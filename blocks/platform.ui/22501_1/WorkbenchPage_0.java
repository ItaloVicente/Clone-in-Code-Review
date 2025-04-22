				if (viewReference != null) {
					E4PartWrapper legacyPart = new E4PartWrapper(part);
					try {
						viewReference.initialize(legacyPart);
					} catch (PartInitException e) {
						WorkbenchPlugin.log(e);
					}
					part.getTransientData().put(E4PartWrapper.E4_WRAPPER_KEY, legacyPart);
					return legacyPart;
