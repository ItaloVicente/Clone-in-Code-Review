		if (searchFlags == ANYWHERE) {
			findElementsRecursive(searchRoot, clazz, matcher, elements, searchFlags);
		} else {
			List<MApplicationElement> el = new ArrayList<MApplicationElement>();

			if ((searchFlags & OUTSIDE_PERSPECTIVE) != 0) {
				findElementsRecursive(searchRoot, clazz, matcher, elements, searchFlags);
				for (MApplicationElement element : findElementByClass(searchRoot, MPerspective.class)) {
					TreeIterator<Object> itt = EcoreUtil.getAllContents((EObject) element, false);
					while (itt.hasNext()) {
						elements.remove(itt.next());// Remove element contain in a perspective
					}
