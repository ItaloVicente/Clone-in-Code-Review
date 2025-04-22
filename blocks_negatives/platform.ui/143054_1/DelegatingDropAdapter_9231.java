        originalDropType = event.detail;
        TransferDropTargetListener oldListener = getCurrentListener();
        updateCurrentListener(event);
        final TransferDropTargetListener newListener = getCurrentListener();
        if (newListener != null && newListener == oldListener) {
            SafeRunnable.run(new SafeRunnable() {
                @Override
