				MUIElement element = (MUIElement) o;
				List<MPart> addedParts = modelService.findElements(element, null, MPart.class, null);
				for (MPart part : addedParts) {
					IWorkbenchPartReference ref = (IWorkbenchPartReference) part.getTransientData()
							.get(IWorkbenchPartReference.class.getName());

					if (ref instanceof EditorReference && getEditorReference(part) == null) {
						addEditorReference((EditorReference) ref);
