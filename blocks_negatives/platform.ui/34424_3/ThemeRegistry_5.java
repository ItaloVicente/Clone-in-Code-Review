    /**
	 * Add a category presentation binding. The given category will only be
	 * availible if the given presentation is active.
	 * 
	 * @param categoryId
	 *            the category id
	 * @param presentationId
	 *            the presentation id
	 * 
	 * @deprecated used by the removal presentation API
	 */
	@Deprecated
    public void addCategoryPresentationBinding(String categoryId,
            String presentationId) {
        Set presentations = (Set) categoryBindingMap.get(categoryId);
        if (presentations == null) {
            presentations = new HashSet();
            categoryBindingMap.put(categoryId, presentations);
        }
        presentations.add(presentationId);
    }

