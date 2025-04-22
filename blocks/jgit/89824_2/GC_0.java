		for (File f : touchedFanout) {
			FileUtils.delete(f
					FileUtils.RECURSIVE | FileUtils.EMPTY_DIRECTORIES_ONLY
							| FileUtils.IGNORE_ERRORS);
		}

