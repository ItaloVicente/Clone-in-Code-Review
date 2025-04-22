		Collections.sort(repositoriesList, new Comparator<Repository>() {
			@Override
			public int compare(Repository o1, Repository o2) {
				String name1 = o1.getWorkTree().getName();
				String name2 = o2.getWorkTree().getName();

				return name1.compareToIgnoreCase(name2);
			}
		});
