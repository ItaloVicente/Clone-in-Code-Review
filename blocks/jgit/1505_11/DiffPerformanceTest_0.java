			testMyers(10000);
			testMyers(20000);
			testMyers(10000);
			testMyers(20000);

			List<PerfData> perfData = new LinkedList<PerfData>();
			perfData.add(testMyers(10000));
			perfData.add(testMyers(20000));
			perfData.add(testMyers(40000));
			perfData.add(testMyers(80000));
			perfData.add(testMyers(160000));
			perfData.add(testMyers(320000));
			perfData.add(testMyers(640000));
			perfData.add(testMyers(1280000));

			Comparator<PerfData> c = getComparator(1);
			double factor = Collections.max(perfData
					/ Collections.min(perfData
			assertTrue(
					"minimun and maximum of performance-index t/(N*D) differed too much. Measured factor of "
							+ factor
							+ " (maxFactor="
							+ maxFactor
							+ "). Perfdata=<" + perfData.toString() + ">"
					factor < maxFactor);
		}
	}

	public void testPatience() {
		if (stopwatch != null) {
			testPatience(10000);
			testPatience(20000);
			testPatience(10000);
			testPatience(20000);
