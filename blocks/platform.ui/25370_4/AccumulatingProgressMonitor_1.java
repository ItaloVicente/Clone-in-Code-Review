		if (collector == null) {
			createCollector(null, null, work);
		} else {
			collector.worked(work);
		}
	}
