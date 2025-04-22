        listMap.clear();
        Object[] children = getSortedChildren(getRoot());
        int size = children.length;

        listRemoveAll();
        String[] labels = new String[size];
        for (int i = 0; i < size; i++) {
            Object el = children[i];
            labels[i] = getLabelProviderText((ILabelProvider) getLabelProvider(),el);
            listMap.add(el);
            mapElement(el, getControl()); // must map it, since findItem only looks in map, if enabled
        }
        listSetItems(labels);
    }

    @Override
