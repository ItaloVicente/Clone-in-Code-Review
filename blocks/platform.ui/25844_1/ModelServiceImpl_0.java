		if (searchRoot instanceof MBindingTable) {
			MBindingTable bindingTable = (MBindingTable) searchRoot;
			for (MKeyBinding child : bindingTable.getBindings()) {
				findElementsRecursive(child, clazz, matcher, elements, searchFlags);
			}
		}

