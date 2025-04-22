				new Comparator<StagingEntry>() {
					@Override
					public int compare(StagingEntry entry1, StagingEntry entry2) {
						return String.CASE_INSENSITIVE_ORDER
								.compare(entry1.getPath(), entry2.getPath());
					}
				});
