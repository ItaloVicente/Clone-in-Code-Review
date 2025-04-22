		Collections.sort(items, new Comparator<CommitItem>() {
			public int compare(CommitItem o1, CommitItem o2) {
				int diff = o1.status.ordinal() - o2.status.ordinal();
				if (diff != 0)
					return diff;
				diff = o1.file.getProject().getName().compareTo(
						o2.file.getProject().getName());
				if (diff != 0)
					return diff;
				return o1.file
				.getProjectRelativePath()
				.toString()
				.compareTo(
						o2.file.getProjectRelativePath().toString());
			}
		});
