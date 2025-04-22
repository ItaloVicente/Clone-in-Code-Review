			markerEntries = new MarkerEntry[totalSize];
			System.arraycopy(allMarkers, start, markerEntries, 0, totalSize);
			for (int i = 0; i < markerEntries.length; i++) {
				String project = allMarkers[i].getMarker().getResource().getProject().getName();
				System.out.println("project name = " + project); //$NON-NLS-1$
				if (!projectIndexes.containsKey(project)) {
					LinkedList<Integer> list = new LinkedList();
					list.add(i);
					projectIndexes.put(project, list);
				} else {
					LinkedList<Integer> list = projectIndexes.get(project);
					list.add(i);
					projectIndexes.put(project, list);
				}
			}
			for (String key : projectIndexes.keySet()) {
				LinkedList<Integer> indexes = projectIndexes.get(key);
				projects.put(new MarkerProject(key, markerEntries, name, indexes), key);
			}
			markerProjects = new MarkerSupportItem[projects.size()];
			int i = 0;
			for (MarkerProject project : projects.keySet()) {
				markerProjects[i] = project;
				i++;
