		if (ls != null) {
			for (File f : ls) {
				if (f.isDirectory()) {
					silent = recursiveDelete(f
				} else if (!f.delete()) {
					if (!silent) {
						reportDeleteFailure(failOnError
					}
