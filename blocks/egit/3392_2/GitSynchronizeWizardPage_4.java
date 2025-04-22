		List<IProject> projectsList = new ArrayList<IProject>(projects.keySet());
		Collections.sort(projectsList, new Comparator<IProject>() {
			public int compare(IProject o1, IProject o2) {
				return o2.getName().compareTo(o1.getName());
			}
		});

		final Object[] array = projectsList.toArray();
