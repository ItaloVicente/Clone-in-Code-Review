        boolean classesEmpty = true;
        if (otherClasses.isEmpty()) {
            classes.clear();
        } else {
            for (int j = 0; j < classes.size(); j++) {
                if (classes.get(j) != null) {
                    if (!otherClasses.contains(classes.get(j))) {
                        classes.set(j, null);
                    }
                }
            }
        }
        return classesEmpty;
    }

    private void removeNonCommonAdapters(List adapters, List classes) {
        for (int i = 0; i < classes.size(); i++) {
            Object o = classes.get(i);
            if (o != null) {
                Class clazz = (Class) o;
                String name = clazz.getName();
                if (adapters.contains(name)) {
