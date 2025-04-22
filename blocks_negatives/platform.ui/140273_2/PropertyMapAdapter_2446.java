        return true;
    }

    /**
     * Detaches all listeners which have been registered with other objects
     *
     * @since 3.1
     */
    public void dispose() {
        if (listeners != null) {
            detachListener();
            listeners = null;
        }
    }

    protected final void firePropertyChange(String prefId) {
        if (ignoreCount > 0) {
            queuedEvents.add(prefId);
            return;
        }

        if (listeners != null) {
            listeners.firePropertyChange(prefId);
        }
    }

    @Override
