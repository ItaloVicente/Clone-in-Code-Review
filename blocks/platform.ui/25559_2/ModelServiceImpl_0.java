				} else if ((searchFlags & IN_PART) != 0) {
					 List<MPart> parts = findElements((MUIElement) searchRoot, null, MPart.class, null);
					 for (MPart part : parts) {
						for (MHandler handler : part.getHandlers()) {
							findElementsRecursive(handler, clazz, matcher, elements, searchFlags);
						}
					 }
				 }
