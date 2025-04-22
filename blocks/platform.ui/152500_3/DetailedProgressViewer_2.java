			lastControl = item;
		}
		for (int i = exIndex; i < existingControls.length; i++) {
			if (existingControls[i] != null) {
				jobItemControls.remove(existingControls[i].getData());
				existingControls[i].dispose();
			}
