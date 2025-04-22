        activeListeners.clear();
        for (int i = 0; i < listeners.size(); i++) {
            final TransferDragSourceListener listener = listeners
                    .get(i);
            SafeRunnable.run(new SafeRunnable() {
                @Override
