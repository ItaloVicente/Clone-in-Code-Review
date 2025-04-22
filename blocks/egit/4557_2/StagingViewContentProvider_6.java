		Set<StagingEntry> nodes = new TreeSet<StagingEntry>(
				new Comparator<StagingEntry>() {
					public int compare(StagingEntry o1, StagingEntry o2) {
						return o1.getPath().compareTo(o2.getPath());
					}
				});

		if (update.changedResources != null
				&& !update.changedResources.isEmpty()) {
			nodes.addAll(Arrays.asList(content));
			for (String res : update.changedResources)
				for (StagingEntry entry : content)
					if (entry.getPath().equals(res))
						nodes.remove(entry);
		}
