        treeContentProvider = contentProvider;
        treeLabelProvider = labelProvider;

        for (Object object : items) {
            setTreeChecked(object, true);
        }
