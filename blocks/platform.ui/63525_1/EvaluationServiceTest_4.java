			processEvents();
			waitForJobs(500, 3000);

			Assume.assumeTrue(window.getShell().isVisible());
			Assume.assumeTrue(PlatformUI.getWorkbench().getActiveWorkbenchWindow() == window);
			Assume.assumeTrue(shellIsActive.get());

