        if (contextListener == null) {
        }

        if (listeners == null) {
            return;
        }

        listeners.remove(contextListener);

        if (listeners.isEmpty()) {
            listeners = null;
        }
    }

    /**
     * The string representation of this context -- for debugging purposes only.
     * This string should not be shown to an end user.
     *
     * @return The string representation; never <code>null</code>.
     */
    @Override
