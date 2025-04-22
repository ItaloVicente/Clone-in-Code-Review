		boolean allWriteable = false;
		if (!selection.isEmpty()) {
			List<?> elements = selection.toList();
			allWriteable = elements.stream()
					.allMatch(x -> x instanceof RefFilter
							&& !((RefFilter) x).isPreconfigured());
		}
		removeButton.setEnabled(allWriteable);
		editButton.setEnabled(selection.size() == 1
				&& !((RefFilter) selection.getFirstElement())
						.isPreconfigured());
