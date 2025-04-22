    }

    /**
     * Returns the array of <code>Transfer</code> added by the application
     */
    /* package */Transfer[] getTransfers() {
        Transfer[] transfers = new Transfer[transferTypes.size()];
        transferTypes.toArray(transfers);
        return transfers;
    }

    /**
     * Returns the drop listener provided by the application.
     */
    /* package */DropTargetListener getDropTargetListener() {
        return dropTargetListener;
    }

    @Override
