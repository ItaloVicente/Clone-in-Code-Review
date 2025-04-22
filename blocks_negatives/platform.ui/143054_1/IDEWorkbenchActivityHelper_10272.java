    public void shutdown() {
        if (listener != null) {
            ResourcesPlugin.getWorkspace().removeResourceChangeListener(
                    listener);
        }
    }
