		DropAgent newDropAgent = dndManager.getDropAgent(dragElement, info);
		if (newDropAgent == dropAgent) {
			if (dropAgent != null) {
				dropAgent = dropAgent.track(dragElement, info) ? dropAgent : null;
			}
		} else {
			if (dropAgent != null) {
				dropAgent.dragLeave(dragElement, info);
			}
			System.out.println("New drop agent: " + newDropAgent);
			dropAgent = newDropAgent;
