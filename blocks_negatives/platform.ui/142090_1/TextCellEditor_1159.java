        LayoutData data = new LayoutData();
        data.minimumWidth= 0;
        return data;
    }

    /**
     * Return the modify listener.
     */
    private ModifyListener getModifyListener() {
        if (modifyListener == null) {
