	private final HashSet<PlotLane> activeLanes = new HashSet<PlotLane>(32);

	@Override
	public void clear() {
		super.clear();
		lanesAllocated = 0;
		freeLanes.clear();
		activeLanes.clear();
	}
