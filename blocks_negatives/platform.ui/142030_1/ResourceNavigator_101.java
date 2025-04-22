        int sortType = ResourceSorter.NAME;
        try {
            int sortInt = 0;
            if (memento != null) {
                String sortStr = memento.getString(TAG_SORTER);
                if (sortStr != null) {
