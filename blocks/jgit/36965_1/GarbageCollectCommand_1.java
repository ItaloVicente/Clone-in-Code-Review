			if (repo instanceof FileRepository) {
				GC gc = new GC((FileRepository) repo);
				return toProperties(gc.getStatistics());
			} else {
				return new Properties();
			}
