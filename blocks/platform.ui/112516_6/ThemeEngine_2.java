	private String getVarientThemeId(String id, String os, String ws) {
		if (os != null) {
			id += '_' + os;
		}
		if (ws != null) {
			id += '-' + ws;
		}
		return id;
	}

	private String getVarientThemeLabel(String label, String os, String ws) {
		String currentOS = Platform.getOS();
		String currentWS = Platform.getWS();
		if (os != null && !os.equals(currentOS)) {
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
			label += " [" + osName;
		}
		if (ws != null && !ws.equals(currentWS)) {
			String wsName;
			switch (ws) {
			case Platform.WS_CARBON:	wsName="Carbon";break;
			case Platform.WS_COCOA:		wsName="Cocoa";break;
			case Platform.WS_GTK:		wsName="GTK";break;
			case Platform.WS_MOTIF:		wsName="Motif";break;
			case Platform.WS_PHOTON:	wsName="Photon";break;
			case Platform.WS_WPF:		wsName="WPF";break;
			default:					wsName=ws;break;
			}
			label += " - " + wsName;
		}
		if (os != null && !os.equals(currentOS)) {
			label += "]";
		}
		return label;
	}

