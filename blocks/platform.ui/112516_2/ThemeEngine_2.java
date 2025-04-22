	private String getOsSpecificThemeId(String id, String os) {
		if (os == null) {
			return id;
		}
		return id + '_' + os;
	}

	private String getOsSpecificThemeLabel(String label, String os) {
		if (os == null) {
			return label;
		}
		String osName;
		switch (os) {
		case Platform.OS_AIX:		osName="AIX";break;
		case Platform.OS_HPUX:		osName="HP/UX";break;
		case Platform.OS_LINUX:		osName="Linux";break;
		case Platform.OS_MACOSX:	osName="Mac OS X";break;
		case Platform.OS_QNX:		osName="QNX";break;
		case Platform.OS_SOLARIS:	osName="Solaris";break;
		case Platform.OS_WIN32:		osName="Windows";break;
		default:					osName=os;break;
		}
		return label + " - " + osName;
	}

