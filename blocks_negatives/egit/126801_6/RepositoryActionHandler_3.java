	/**
	 * Creates an array of the given class type containing all the objects in
	 * the selection that adapt to the given class.
	 *
	 * @param selection
	 * @param c
	 * @return the selected adaptables
	 */
	private <T> List<T> getSelectedAdaptables(ISelection selection,
			Class<T> c) {
		List<T> result;
		if (selection != null && !selection.isEmpty()) {
			result = new ArrayList<>();
			Iterator elements = ((IStructuredSelection) selection).iterator();
			while (elements.hasNext()) {
				T adapter = AdapterUtils.adapt(elements.next(), c);
				if (adapter != null) {
					result.add(adapter);
				}
			}
		} else {
			result = Collections.emptyList();
		}
		return result;
	}

