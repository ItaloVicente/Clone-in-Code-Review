        if (state) {
            if (checkedListItems == null) {
                grayCheckHierarchy(currentTreeSelection);
                checkedListItems = (List) checkedStateStore
                        .get(currentTreeSelection);
            }
            checkedListItems.add(listElement);
        } else {
            checkedListItems.remove(listElement);
            if (checkedListItems.isEmpty()) {
                ungrayCheckHierarchy(currentTreeSelection);
            }
        }

        if (checkedListItems.size() > 0) {
