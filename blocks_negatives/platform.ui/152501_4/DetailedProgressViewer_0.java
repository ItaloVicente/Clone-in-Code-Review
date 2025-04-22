		Set<Object> newItems = new HashSet<>(elements.length);

		Control[] existingChildren = control.getChildren();
		for (Control element : existingChildren) {
			if (element.getData() != null)
				newItems.add(element.getData());
		}

