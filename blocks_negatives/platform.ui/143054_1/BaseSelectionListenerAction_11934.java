        running = true;
        try {
            run();
        } finally {
            running = false;
            IStructuredSelection s = deferredSelection;
            deferredSelection = null;
            if (s != null) {
                selectionChanged(s);
            }
        }
    }
