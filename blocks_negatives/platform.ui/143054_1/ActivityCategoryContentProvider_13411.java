        if (parentElement instanceof IActivityManager) {
            Set categoryIds = manager.getDefinedCategoryIds();
            ArrayList categories = new ArrayList(categoryIds.size());
            for (Iterator i = categoryIds.iterator(); i.hasNext();) {
                String categoryId = (String) i.next();
                ICategory category = manager.getCategory(categoryId);
