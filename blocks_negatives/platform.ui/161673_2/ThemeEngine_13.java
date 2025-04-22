	@Override
	public void setTheme(String themeId, boolean restore) {
		String osVersion = System.getProperty("os.version");
		if (osVersion != null) {
			boolean found = false;
			for (Theme t : themes) {
				String osVersionList = t.getOsVersion();
				if (osVersionList != null) {
					String[] osVersions = osVersionList.split(",); //$NON-NLS-1$
-					for (String osVersionFromTheme : osVersions) {
-						if (osVersionFromTheme != null && osVersion.contains(osVersionFromTheme)) {
-							String themeVersion = themeId + osVersionList;
-							if (t.getId().equals(themeVersion)) {
-								setTheme(t, restore);
-								found = true;
-								break;
+		@Override
+		public void setTheme(String themeId, boolean restore) {
+			String osVersion = System.getProperty(os.version");
