			} else if ("org.eclipse.e4.primaryDataStack".equals(category)) { //$NON-NLS-1$
				MElementContainer<? extends MUIElement> container = getContainer();
				MUIElement area = modelService.find("org.eclipse.ui.editorss", container); //$NON-NLS-1$

				MPartStack activeStack = null;
				if (area instanceof MPlaceholder
						&& ((MPlaceholder) area).getRef() instanceof MArea) {
					MArea a = (MArea) ((MPlaceholder) area).getRef();
					MUIElement curActive = a.getSelectedElement();
					while (curActive instanceof MElementContainer<?>) {
						if (curActive instanceof MPartStack) {
							activeStack = (MPartStack) curActive;
							break;
