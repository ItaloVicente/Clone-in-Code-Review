	static IOperatingSystemRegistration getInstance() {
		if (Platform.OS_MACOSX.equals(Platform.getOS())) {
			return new RegistrationMacOsX();
		} else if (Platform.OS_LINUX.equals(Platform.getOS())) {
			return new RegistrationLinux();
		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return new RegistrationWindows();
