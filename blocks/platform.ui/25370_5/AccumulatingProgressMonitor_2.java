		currentTask = name;
		if (collector == null) {
			createCollector(name, null, 0);
		} else {
			collector.setTaskName(name);
		}
	}
