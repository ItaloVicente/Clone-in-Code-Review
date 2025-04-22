        if (!Util.equals(id, castedObject.id)) {
            return false;
        }

        return Util.equals(name, castedObject.name);
    }

    void fireCategoryChanged(CategoryEvent categoryEvent) {
        if (categoryEvent == null) {
