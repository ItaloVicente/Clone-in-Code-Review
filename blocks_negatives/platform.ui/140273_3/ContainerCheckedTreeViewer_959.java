        Object[] checked = super.getCheckedElements();
        ArrayList result = new ArrayList();
        for (Object curr : checked) {
            result.add(curr);
            Widget item = findItem(curr);
            if (item != null) {
                Item[] children = getChildren(item);
                if (children.length == 1 && children[0].getData() == null) {
                    collectChildren(curr, result);
                }
            }
        }
        return result.toArray();
    }
