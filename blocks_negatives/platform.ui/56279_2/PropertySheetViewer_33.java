        List children = getChildren(node);

        Set set = new HashSet(childItems.length * 2 + 1);

        for (int i = 0; i < childItems.length; i++) {
            Object data = childItems[i].getData();
            if (data != null) {
                Object e = data;
                int ix = children.indexOf(e);
                    removeItem(childItems[i]);
                    set.add(e);
                }
                childItems[i].dispose();
            }
        }

        int oldCnt = -1;
        if (widget == tree) {
