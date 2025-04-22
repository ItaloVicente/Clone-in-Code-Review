		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				FONTDATA_ARRAY_DEFAULT_DEFAULT = getFontDataArrayDefaultDefault();
				FONTDATA_DEFAULT_DEFAULT = getFontDataArrayDefaultDefault()[0];
			}
