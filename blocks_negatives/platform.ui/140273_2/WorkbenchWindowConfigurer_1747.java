        if (actionBarConfigurer == null) {
            actionBarConfigurer = new WindowActionBarConfigurer();
        }
        return actionBarConfigurer;
    }

    /**
     * Returns whether the given id is for a cool item.
     *
     * @param the item id
     * @return <code>true</code> if it is a cool item,
     * and <code>false</code> otherwise
     */
    /* package */boolean containsCoolItem(String id) {
        getActionBarConfigurer();
        return actionBarConfigurer.containsCoolItem(id);
    }

    @Override
