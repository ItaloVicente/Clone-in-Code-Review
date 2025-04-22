        if (dataLocation != null) {
	        String readWritePath = dataLocation.append(FN_DIALOG_SETTINGS)
	                .toOSString();
	        File settingsFile = new File(readWritePath);
	        if (settingsFile.exists()) {
	            try {
	                dialogSettings.load(readWritePath);
	            } catch (IOException e) {
	            }

	            return;
	        }
        }
