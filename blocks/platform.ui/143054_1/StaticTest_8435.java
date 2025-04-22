					.contains(new CategoryActivityBinding(
							"org.eclipse.activity" + Integer.toString(index),
							first_category.getId())));
		}
		try {
			assertTrue(first_category.getDescription().equals("description"));
		} catch (NotDefinedException e) {
			e.printStackTrace();
		}
		assertTrue(first_category.getId().equals("org.eclipse.category1"));
		try {
			assertTrue(first_category.getName().equals("Category 1"));
		} catch (NotDefinedException e) {
			e.printStackTrace();
