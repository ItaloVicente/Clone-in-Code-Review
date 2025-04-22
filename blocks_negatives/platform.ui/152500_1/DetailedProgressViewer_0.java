		Set<JobTreeElement> newItems = new LinkedHashSet<>(elements.length);

		Control[] existingChildren = control.getChildren();
		for (Control child : existingChildren) {
			if (child.getData() != null) {
				newItems.add((JobTreeElement) child.getData());
			}
		}

