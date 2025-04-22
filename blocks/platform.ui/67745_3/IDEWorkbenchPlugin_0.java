	public synchronized UnassociatedEditorStrategyRegistry getUnassociatedEditorStrategyRegistry() {
		if (unassociatedEditorStrategyRegistry == null) {
			unassociatedEditorStrategyRegistry = new UnassociatedEditorStrategyRegistry();
		}
		return unassociatedEditorStrategyRegistry;
	}
