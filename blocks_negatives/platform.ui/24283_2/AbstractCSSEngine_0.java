		} else if (elementProvider == null) {
			Object tmp = widgetsMap.get(element.getClass().getName());
			Class parent = element.getClass();
			while (tmp == null && parent != Object.class) {
				parent = parent.getSuperclass();
				tmp = widgetsMap.get(parent.getName());
			}
			if(tmp != null && tmp instanceof IElementProvider) {
				elt = ((IElementProvider)tmp).getElement(element, this);
			}
