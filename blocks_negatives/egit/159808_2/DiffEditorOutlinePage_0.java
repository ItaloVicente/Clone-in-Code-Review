			for (FileDiffRegion range : ranges) {
				String path = range.getDiff().getPath();
				int i = path.lastIndexOf('/');
				if (i > 0) {
					path = path.substring(0, i);
				} else {
