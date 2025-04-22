		String javaVendor = System.getProperty(JAVA_VENDOR); // $NON-NLS-1$
		if (javaVendor != null) {
			osInfo += String.format("%nJava vendor: %s", javaVendor);//$NON-NLS-1$
		}

		String javaVendorVersion = System.getProperty(JAVA_VENDOR_VERSION); // $NON-NLS-1$
		if (javaVendorVersion != null) {
			osInfo += String.format("%nJava vendor version: %s", javaVendorVersion);//$NON-NLS-1$
		}

		String javaRuntimeVersion = System.getProperty(JAVA_RUNTIME_VERSION); // $NON-NLS-1$
		if (javaRuntimeVersion != null) {
			osInfo += String.format("%nJava runtime version: %s", javaRuntimeVersion);//$NON-NLS-1$
		}

