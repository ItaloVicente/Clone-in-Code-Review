        List childEntries = getFilteredEntries(rootEntry.getChildEntries());

        if (childEntries.size() == 0) {
            categories = new PropertySheetCategory[0];
            return;
        }

        Map categoryCache = new HashMap(categories.length * 2 + 1);
        for (int i = 0; i < categories.length; i++) {
            categories[i].removeAllEntries();
            categoryCache.put(categories[i].getCategoryName(), categories[i]);
        }

        List categoriesToRemove = new ArrayList(Arrays.asList(categories));

        PropertySheetCategory misc = (PropertySheetCategory) categoryCache
                .get(MISCELLANEOUS_CATEGORY_NAME);
        if (misc == null) {
