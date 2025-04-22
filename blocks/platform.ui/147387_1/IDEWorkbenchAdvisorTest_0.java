	@Test
	public void testWorkSpacePersistence() {
		final Path[] filePaths = new Path[1];

		IDEWorkbenchAdvisor advisor = new IDEWorkbenchAdvisor() {
			private IJobChangeListener listener = new JobChangeAdapter() {
				@Override
				public void done(IJobChangeEvent event) {
					event.getJob().cancel();

					Job[] jobs = Job.getJobManager().find(Workbench.WORKBENCH_AUTO_SAVE_JOB);
					for (Job job : jobs) {
						try {
							job.join();
						} catch (InterruptedException e) {
						}
					}

					PlatformUI.getWorkbench().close();
				}
			};

			@Override
			public void postStartup() {
				super.postStartup();

				for (Job job : Job.getJobManager().find(null)) {
					if (job.getName().equals(Workbench.WORKBENCH_AUTO_SAVE_JOB)) {
						job.cancel();
						job.addJobChangeListener(listener);
						job.schedule();
						break;
					}
				}
			}

			@Override
			public boolean preShutdown() {
				ResourceHandler handler = (ResourceHandler) PlatformUI.getWorkbench()
						.getService(IModelResourceHandler.class);
				assertNotNull(handler);

				try {
					Path filePath = handler.getWorkbenchSaveLocation().toPath();
					filePaths[0] = filePath;

					Files.copy(filePath, filePath.resolveSibling("autosave.xmi"));

					handler.save();
					Files.copy(filePath, filePath.resolveSibling("preshutdown.xmi"));

					Resource resource = handler.getTheResource();
					resource.save(null);
					Files.copy(filePath, filePath.resolveSibling("preshutdown_nofilter.xmi"));
				} catch (IOException e) {
					e.printStackTrace();
				}

				return super.preShutdown();
			}

			@Override
			public void postShutdown() {
				super.postShutdown();
				ResourceHandler handler = (ResourceHandler) PlatformUI.getWorkbench()
						.getService(IModelResourceHandler.class);

				try {
					handler.save();
					Path filePath = handler.getWorkbenchSaveLocation().toPath();
					Files.copy(filePath, filePath.resolveSibling("postshutdown.xmi"));

					Resource resource = handler.getTheResource();
					resource.save(null);
					Files.copy(filePath, filePath.resolveSibling("postshutdown_nofilter.xmi"));

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		};
		int returnCode = PlatformUI.createAndRunWorkbench(display, advisor);
		Assert.assertEquals(PlatformUI.RETURN_OK, returnCode);

		try {
			Files.copy(filePaths[0], filePaths[0].resolveSibling("aftershutdown.xmi"));
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

