		try {
			fixedModelRegistry.removeCategory(category_to_listen.getId(),
					category_to_listen.getName()); //$NON-NLS-1$
		} catch (NotDefinedException e) {
			e.printStackTrace(System.err);
		}
		assertTrue(listenerType == -1);
		listenerType = 5;
		fixedModelRegistry
				.addCategory(category_to_listen.getId(), "Category 6"); //$NON-NLS-1$ //$NON-NLS-2$
		assertTrue(listenerType == -1);
		listenerType = 8;
		fixedModelRegistry.addCategoryActivityBinding((String) activityManager
				.getDefinedActivityIds().toArray()[4], category_to_listen
				.getId()); //$NON-NLS-1$
		assertTrue(listenerType == -1);
		listenerType = 8;
		fixedModelRegistry.removeCategoryActivityBinding(
				(String) activityManager.getDefinedActivityIds().toArray()[4],
				category_to_listen.getId());//$NON-NLS-1$
		listenerType = 10;
		fixedModelRegistry.updateCategoryDescription(
				category_to_listen.getId(), "description_change"); //$NON-NLS-1$
		try {
			assertTrue(category_to_listen.getDescription().equals(
					"description_change")); //$NON-NLS-1$
		} catch (NotDefinedException e) {
			e.printStackTrace(System.err);
		}
		assertTrue(listenerType == -1);
		listenerType = 7;
		fixedModelRegistry.updateCategoryName(category_to_listen.getId(),
				"name_change"); //$NON-NLS-1$
		try {
			assertTrue(category_to_listen.getName().equals("name_change")); //$NON-NLS-1$
		} catch (NotDefinedException e) {
			e.printStackTrace(System.err);
		}
		assertTrue(listenerType == -1);
	}
