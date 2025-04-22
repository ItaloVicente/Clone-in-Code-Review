		super.init(site, memento);
		this.memento = memento;
	}

	void initDrillDownAdapter(TreeViewer viewer) {
		DrillDownAdapter drillDownAdapter = new DrillDownAdapter(viewer) {
			@Override
