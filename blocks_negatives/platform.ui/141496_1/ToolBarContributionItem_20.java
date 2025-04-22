        if (toolBarManager != null) {
            toolBarManager.dispose();
            toolBarManager.removeAll();
            toolBarManager = null;
        }

        /*
         * We need to dispose the cool item or we might be left holding a cool
         * item with a disposed control.
         */
        if ((coolItem != null) && (!coolItem.isDisposed())) {
            coolItem.dispose();
            coolItem = null;
        }
        if (chevronMenuManager != null) {
            chevronMenuManager.dispose();
        }
        disposed = true;
    }

    @Override
