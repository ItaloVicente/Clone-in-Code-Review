        if (listeners == null) {
            listeners = new PropertyListenerList();
            attachListener();
        }
        listeners.add(listener);
    }

    @Override
