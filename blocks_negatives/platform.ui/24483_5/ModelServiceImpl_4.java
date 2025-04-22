						findElementsRecursive(active, clazz, matcher, elements, searchFlags);
					}
				} else if ((searchFlags & IN_SHARED_AREA) != 0 && searchRoot instanceof MUIElement) {
					List<MArea> areas = findElements((MUIElement) searchRoot, null, MArea.class,null);
					for (MArea area : areas) {
						findElementsRecursive(area, clazz, matcher, elements, searchFlags);
