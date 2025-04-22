        super.init(site, memento);
        this.memento = memento;
    }

    /**
     * Adds drag and drop support to the bookmark navigator.
     */
    protected void initDragAndDrop() {
        int operations = DND.DROP_COPY;
        Transfer[] transferTypes = new Transfer[] {
                MarkerTransfer.getInstance(), TextTransfer.getInstance() };
        DragSourceListener listener = new DragSourceAdapter() {
            @Override
