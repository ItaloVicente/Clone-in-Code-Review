		ILaunchConfiguration launch = LaunchFinder
				.getRunningLaunchConfiguration(
						Collections.singleton(repository), null);
		if (launch != null) {
			message += "\n\n" + MessageFormat.format( //$NON-NLS-1$
					UIText.LaunchFinder_RunningLaunchMessage, launch.getName());
		}
		final String question = message;
