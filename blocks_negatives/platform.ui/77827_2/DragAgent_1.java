		DropAgent curAgent = dropAgent;

		if (dropAgent != null)
			dropAgent = dropAgent.track(dragElement, info) ? dropAgent : null;

		if (dropAgent == null) {
			if (curAgent != null)
				curAgent.dragLeave(dragElement, info);

			dropAgent = dndManager.getDropAgent(dragElement, info);

