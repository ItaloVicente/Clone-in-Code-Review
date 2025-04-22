    }

    boolean setCategoryActivityBindings(Set categoryActivityBindings) {
        categoryActivityBindings = Util.safeCopy(categoryActivityBindings,
                ICategoryActivityBinding.class);

        if (!Util.equals(categoryActivityBindings,
                this.categoryActivityBindings)) {
            this.categoryActivityBindings = categoryActivityBindings;
            this.categoryActivityBindingsAsArray = (ICategoryActivityBinding[]) this.categoryActivityBindings
                    .toArray(new ICategoryActivityBinding[this.categoryActivityBindings
                            .size()]);
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    boolean setDefined(boolean defined) {
        if (defined != this.defined) {
            this.defined = defined;
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    boolean setName(String name) {
        if (!Util.equals(name, this.name)) {
            this.name = name;
            hashCode = HASH_INITIAL;
            string = null;
            return true;
        }

        return false;
    }

    @Override
