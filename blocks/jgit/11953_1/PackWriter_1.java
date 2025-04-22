	private void singleThreadDeltaSearch(ProgressMonitor monitor
			ObjectToPack[] list
		long totalWeight = 0;
		for (int i = 0; i < cnt; i++) {
			ObjectToPack o = list[i];
			if (!o.isEdge() && !o.doNotAttemptDelta())
				totalWeight += o.getWeight();
