        List<?> items;
        if (root == null) {
            items = Collections.emptyList();
        } else {
            items = getAllWhiteCheckedItems();
            for (Object object : items) {
                setTreeChecked(object, false);
            }
        }

