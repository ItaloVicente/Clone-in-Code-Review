	@Benchmark
	@BenchmarkMode({ Mode.AverageTime })
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	@Warmup(iterations = 2
	@Measurement(iterations = 2
	public void testGetRefsByPrefix(Blackhole blackhole
		String branchPrefix = "refs/heads/branch/" + branchIndex.incrementAndGet() % 100 + "/";
		blackhole.consume(state.repo.getRefDatabase().getRefsByPrefix(branchPrefix));
	}

