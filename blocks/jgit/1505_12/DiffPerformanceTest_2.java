	private PerfData testHistogram(int characters) {
		final HistogramDiff pd = new HistogramDiff();
		pd.setFallbackAlgorithm(null);

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
			diff = pd.diff(cmp
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

