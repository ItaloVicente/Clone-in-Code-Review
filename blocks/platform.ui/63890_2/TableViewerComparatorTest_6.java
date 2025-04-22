	public void testViewerComparatorViolatesGeneralContract() throws Exception {
		Random rand = new Random(0);
		Comparator<String> violatingComparator = new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return rand.nextInt(3) - 1;
			}
		};
		fViewer.setComparator(new ViewerComparator(violatingComparator));

		String[] members = new String[30000];
		for (int i = 0; i < members.length; i++) {
			StringBuilder name = rand.ints(5, 'A', 'Z').collect(StringBuilder::new, StringBuilder::appendCodePoint,
					StringBuilder::append);
			members[i] = name.toString();
		}
		Team randTeam = new Team("RAND", members);

		IllegalArgumentException exception = null;
		ILogger logger = Policy.getLog();
		Policy.setLog(new ILogger() {
			@Override
			public void log(IStatus status) {
			}
		});
		try {
			fViewer.setInput(randTeam);
		} catch (IllegalArgumentException e) {
			exception = e;
			Policy.setLog(logger);
		}
		assertNotNull(exception);
	}

	public void testViewerComparatorViolatesGeneralContract2() throws Exception {
		List<Integer> timSortTestList = new ArrayList<Integer>();
		{
			for (int i = 0; i < 64; ++i) {
				timSortTestList.add(i);
			}
			Collections.shuffle(timSortTestList, new Random(0));
		}
		Comparator<Integer> broken = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1 < 5) {
					return 1;
				}
				return Integer.compare(o1, o2);
			}
		};
		Collections.sort(timSortTestList, broken); // throws up

		Random rand = new Random(0);
		fViewer.setLabelProvider(new TeamModelLabelProvider() {
			@Override
			public String getText(Object element) {
				return Math.abs(rand.nextInt()) + ".";
			}
		});

		String[] members = new String[300];
		for (int i = 0; i < members.length; i++) {
			String name;
			if (i % 3 == 0) {
				name = Integer.toString(i);
			} else {
				name = Integer.toString(i - 3);
			}
			members[i] = name.toString();
		}
		Team randTeam = new Team("RAND", members);

		IllegalArgumentException exception = null;
		try {
			fViewer.setComparator(new ViewerComparator());
			fViewer.setInput(randTeam);
		} catch (IllegalArgumentException e) {
			exception = e;
		}
		assertNotNull(exception);
	}

