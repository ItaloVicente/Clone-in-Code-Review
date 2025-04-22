				if (locations.length == 0) {
					return null;
				}
				IPath location = locations[0];
				if (location == null) {
					return null;
				}
				CompareUtils.compare(location, repository, Constants.HEAD,
						GitFileRevision.INDEX, true, workBenchPage);
