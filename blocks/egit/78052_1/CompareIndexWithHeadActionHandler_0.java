			if (locations.length == 0) {
				return;
			}
			IPath location = locations[0];
			if (location == null) {
				return;
			}
			CompareUtils.compare(location, repository,
					GitFileRevision.INDEX, Constants.HEAD, false,
					workBenchPage);
