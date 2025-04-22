		if (searchFlags == ANYWHERE) {
			findAllChildren(searchRoot, clazz, matcher, elements, searchFlags, true);
		} else {
			List<MApplicationElement> el = new ArrayList<MApplicationElement>();

			if ((searchFlags & OUTSIDE_PERSPECTIVE) != 0) {
				findAllChildren(searchRoot, clazz, matcher, elements, searchFlags, true);
				for (MApplicationElement element : findElementsByClass(searchRoot,
						MPerspective.class)) {
					TreeIterator<Object> itt = EcoreUtil.getAllContents((EObject) element, false);
					while (itt.hasNext()) {
						elements.remove(itt.next());// Remove element contain in a perspective
