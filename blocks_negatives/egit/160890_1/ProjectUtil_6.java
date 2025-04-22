		Arrays.sort(allProjects, new Comparator<IProject>() {
			@Override
			public int compare(IProject o1, IProject o2) {
				IPath l1 = o1.getLocation();
				IPath l2 = o2.getLocation();
				if (l1 != null && l2 != null)
					return l2.toFile().compareTo(l1.toFile());
				else if (l1 != null)
					return -1;
				else if (l2 != null)
					return 1;
				else
					return 0;
			}

