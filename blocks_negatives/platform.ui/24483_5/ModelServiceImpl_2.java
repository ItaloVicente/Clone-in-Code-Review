		if (searchRoot instanceof MBindingContext && (searchFlags == ANYWHERE)) {
			MBindingContext bindingContext = (MBindingContext) searchRoot;
			for (MBindingContext child : bindingContext.getChildren()) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
			}
		}
