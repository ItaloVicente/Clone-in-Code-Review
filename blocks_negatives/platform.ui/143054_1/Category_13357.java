        final Category castedObject = (Category) object;
        if (!Util.equals(categoryActivityBindings,
                castedObject.categoryActivityBindings)) {
            return false;
        }

        if (!Util.equals(defined, castedObject.defined)) {
            return false;
        }
