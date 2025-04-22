		new ReftableWriter()
				.setMinUpdateIndex(updateIndex)
				.setMaxUpdateIndex(updateIndex)
				.begin(buffer)
				.sortAndWriteRefs(refs)
				.finish();
