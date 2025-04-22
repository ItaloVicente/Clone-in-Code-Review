        try {
            fixedModelRegistry.removeCategory(category_to_listen.getId(),
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
        assertTrue(listenerType == -1);
        listenerType = 8;
        fixedModelRegistry.removeCategoryActivityBinding(
                (String) activityManager.getDefinedActivityIds().toArray()[4],
        listenerType = 10;
        fixedModelRegistry.updateCategoryDescription(
                category_to_listen.getId(), "description_change"); //$NON-NLS-1$
        try {
            assertTrue(category_to_listen.getDescription().equals(
        } catch (NotDefinedException e) {
            e.printStackTrace(System.err);
        }
        assertTrue(listenerType == -1);
        listenerType = 7;
        fixedModelRegistry.updateCategoryName(category_to_listen.getId(),
        try {
        } catch (NotDefinedException e) {
            e.printStackTrace(System.err);
        }
        assertTrue(listenerType == -1);
    }
