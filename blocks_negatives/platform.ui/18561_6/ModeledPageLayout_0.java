				MUIElement parent = psc.getParent();
				while (parent != null && !(parent instanceof MPerspective)) {
					parent.setToBeRendered(true);
					parent = parent.getParent();
				}
				psc.setToBeRendered(true);
