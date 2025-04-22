
		private List<IPath> getSelectedPaths() {
			List<IPath> paths = new ArrayList<IPath>();
			Iterator iterator = selection.iterator();
			while (iterator.hasNext()) {
				StagingEntry stagingEntry = (StagingEntry) iterator.next();
				paths.add(stagingEntry.getLocation());
			}
			return paths;
		}
