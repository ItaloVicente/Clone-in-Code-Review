		String value = System.getProperty("swt.enable.autoScale"); //$NON-NLS-1$
		boolean autoScaleEnable = true;
		if (value != null && "false".equalsIgnoreCase(value)) //$NON-NLS-1$
			autoScaleEnable = false;
		float scaleFactor = 1f;
		if (autoScaleEnable) {
			String deviceZoomStr = System.getProperty("org.eclipse.swt.internal.deviceZoom"); //$NON-NLS-1$
			int currentDeviceZoom = deviceZoomStr != null ? Integer.parseInt(deviceZoomStr) : 100;
			scaleFactor = 100f / currentDeviceZoom;
			if (scaleFactor != 1f) {
				progressRect.x = Math.round(progressRect.x * scaleFactor);
				progressRect.y = Math.round(progressRect.y * scaleFactor);
				progressRect.width = Math.round(progressRect.width * scaleFactor);
				progressRect.height = Math.round(progressRect.height * scaleFactor);
			}
		}
