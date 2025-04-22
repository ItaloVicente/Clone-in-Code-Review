					Job.getJobManager().cancel(Workbench.WORKBENCH_AUTO_SAVE_JOB);
				}
				if (!"false".equalsIgnoreCase(System.getProperty(PlatformUI.PLUGIN_ID + ".testsWaitForEarlyStartup"))) {  //$NON-NLS-1$ //$NON-NLS-2$
					waitForEarlyStartup();
				}
			    getTestHarness().runTests();
			};
