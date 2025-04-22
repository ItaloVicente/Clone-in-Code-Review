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
        }
    }
