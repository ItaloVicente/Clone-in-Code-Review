		List<Repository> projectsList = new ArrayList<Repository>(
				resources.keySet());
		Collections.sort(projectsList, new Comparator<Repository>() {
			public int compare(Repository o1, Repository o2) {
				String name1 = o1.getWorkTree().getName();
				String name2 = o2.getWorkTree().getName();
				return name2.compareTo(name1);
