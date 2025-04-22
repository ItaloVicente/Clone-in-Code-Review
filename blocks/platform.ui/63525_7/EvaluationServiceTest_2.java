			processEvents();
			waitForJobs(500, 3000);

			Assume.assumeTrue(window.getShell().isVisible());
			Assume.assumeTrue(shellIsActive.get());

