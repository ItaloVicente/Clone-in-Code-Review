        Object[] array = listeners.getListeners();
        for (int i = 0; i < array.length; i++) {
            final ISelectionListener l = (ISelectionListener) array[i];
            if ((part != null && sel != null)
                    || l instanceof INullSelectionListener) {

