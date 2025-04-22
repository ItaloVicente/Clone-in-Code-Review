    public void dispose() {
        if (hasListeners()) {
            action.removePropertyChangeListener(propertyChangeListener);
        }
    }

