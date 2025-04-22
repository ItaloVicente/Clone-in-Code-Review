		}

		final IResource[] resources = getSelectedResources(event);
		if (resources.length != 1) {
			return null;
		}

		Job job = new Job(UIText.CompareUtils_jobName) {
