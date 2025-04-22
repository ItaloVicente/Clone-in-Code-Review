	private String getLaunchMessage(Repository repository) {
		ILaunchConfiguration launch = LaunchFinder
				.getRunningLaunchConfiguration(
						Collections.singleton(repository), null);
		if (launch != null) {
			return MessageFormat.format(
					UIText.LaunchFinder_RunningLaunchMessage, launch.getName());
		}
		return null;
	}

