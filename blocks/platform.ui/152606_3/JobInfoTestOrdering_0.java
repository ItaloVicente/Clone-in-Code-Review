	@Test
	public void testJobPriorityOrdering() {
		List<JobInfo> jobInfos = new ArrayList<>();
		Job job;

		job = new TestJob("TestJob");
		job.setPriority(Job.DECORATE);
		jobInfos.add(new ExtendedJobInfo(job));

		job = new TestJob("TestJob");
		job.setPriority(Job.BUILD);
		jobInfos.add(new ExtendedJobInfo(job));

		job = new TestJob("TestJob");
		job.setPriority(Job.LONG);
		jobInfos.add(new ExtendedJobInfo(job));

		job = new TestJob("TestJob");
		job.setPriority(Job.SHORT);
		jobInfos.add(new ExtendedJobInfo(job));

		job = new TestJob("TestJob");
		job.setPriority(Job.INTERACTIVE);
		jobInfos.add(new ExtendedJobInfo(job));

		Collections.shuffle(jobInfos);
		Collections.sort(jobInfos);
		assertEquals(Job.INTERACTIVE, jobInfos.get(0).getJob().getPriority());
		assertEquals(Job.SHORT,       jobInfos.get(1).getJob().getPriority());
		assertEquals(Job.LONG,        jobInfos.get(2).getJob().getPriority());
		assertEquals(Job.BUILD,       jobInfos.get(3).getJob().getPriority());
		assertEquals(Job.DECORATE,    jobInfos.get(4).getJob().getPriority());
	}
