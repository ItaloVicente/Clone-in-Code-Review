		String iconPath = "/icons/full/";//$NON-NLS-1$
		if (isMarkerType(marker, IMarker.PROBLEM)) {
			switch (marker.getAttribute(IMarker.SEVERITY,
					IMarker.SEVERITY_WARNING)) {
			case IMarker.SEVERITY_ERROR:
				return iconPath + "obj16/error_tsk.png";//$NON-NLS-1$
			case IMarker.SEVERITY_WARNING:
				return iconPath + "obj16/warn_tsk.png";//$NON-NLS-1$
			case IMarker.SEVERITY_INFO:
				return iconPath + "obj16/info_tsk.png";//$NON-NLS-1$
			}
		}
		return null;
	}
