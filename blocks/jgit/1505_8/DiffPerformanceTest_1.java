	public void testPatience() {
		if (stopwatch != null) {
			testPatience(10000);
			testPatience(20000);
			testPatience(10000);
			testPatience(20000);

			List<PerfData> perfData = new LinkedList<PerfData>();
			perfData.add(testPatience(10000));
			perfData.add(testPatience(20000));
			perfData.add(testPatience(40000));
			perfData.add(testPatience(80000));
			perfData.add(testPatience(160000));
			perfData.add(testPatience(320000));
			perfData.add(testPatience(640000));
			perfData.add(testPatience(1280000));

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

	private PerfData testPatience(int characters) {
		PerfData ret = new PerfData();
		String a = DiffTestDataGenerator.generateSequence(characters
		String b = DiffTestDataGenerator.generateSequence(characters
		CharArray ac = new CharArray(a);
		CharArray bc = new CharArray(b);
		CharCmp cmp = new CharCmp();
		int cpuTimeChanges = 0;
		long lastReadout = 0;
		long interimTime = 0;
		int repetitions = 0;
		stopwatch.start();
		EditList diff = null;
		while (cpuTimeChanges < minCPUTimerTicks
				&& interimTime < longTaskBoundary) {
			diff = new PatienceDiff().diff(cmp
			repetitions++;
			interimTime = stopwatch.readout();
			if (interimTime != lastReadout) {
				cpuTimeChanges++;
				lastReadout = interimTime;
			}
		}
		ret.runningTime = stopwatch.stop() / repetitions;
		ret.N = ac.size() + bc.size();
		ret.D = diff.size();

		return ret;
	}

