		if (collector == null) {
			createCollector(null, name, 0);
		} else {
			collector.subTask(name);
		}
	}
