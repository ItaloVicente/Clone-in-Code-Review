				WorkbenchPlugin.unsetSplashShell(display);
				return instanceLocationCheck;
			}

			int returnCode = PlatformUI.createAndRunWorkbench(display,
					new IDEWorkbenchAdvisor(processor));

			if (returnCode != PlatformUI.RETURN_RESTART) {
