				MUIElement partElement = ph.getRef();
				if (partElement instanceof MPart) {
					MPart part = (MPart) partElement;
					if (part.getIconURI() == null) {
						MPartDescriptor desc = getPartDescriptor(part.getElementId());
						if (desc != null) {
							part.setIconURI(desc.getIconURI());
						}
					}
