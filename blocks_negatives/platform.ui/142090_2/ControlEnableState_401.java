    /**
     * Restores the window enable state saved in this object.
     */
    public void restore() {
        int size = states.size();
        for (int i = 0; i < size; i++) {
            states.get(i).restore();
        }
    }
