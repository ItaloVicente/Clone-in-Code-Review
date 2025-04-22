				if (ph.getRef() != null
						&& (!(ph.getRef() instanceof MArea) || (searchFlags & EModelService.IN_SHARED_AREA) != 0)) {
					findElementsRecursive(ph.getRef(), clazz, matcher, elements, searchFlags);
				}
			}
			if (clazz != null && clazz.isInstance(element)) {
				match(matcher, element, elements);
			} else if (clazz == null) {
				match(matcher, element, elements);
