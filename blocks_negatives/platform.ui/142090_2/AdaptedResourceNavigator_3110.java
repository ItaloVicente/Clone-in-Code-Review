        super.init(site, memento);
        this.memento = memento;
    }

    /**
     * Initializes a drill down adapter on the viewer.
     */
    void initDrillDownAdapter(TreeViewer viewer) {
        DrillDownAdapter drillDownAdapter = new DrillDownAdapter(viewer) {
            @Override
