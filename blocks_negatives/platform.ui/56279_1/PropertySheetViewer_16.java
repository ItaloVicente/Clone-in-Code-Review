        List filteredEntries = new ArrayList(entries.length);
        for (int i = 0; i < entries.length; i++) {
            IPropertySheetEntry entry = entries[i];
            if (entry != null) {
                String[] filters = entry.getFilters();
                boolean expert = false;
                if (filters != null) {
                    for (int j = 0; j < filters.length; j++) {
                        if (filters[j].equals(IPropertySheetEntry.FILTER_ID_EXPERT)) {
                            expert = true;
                            break;
                        }
                    }
                }
                if (!expert) {
