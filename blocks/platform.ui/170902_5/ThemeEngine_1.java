	private List<String> getAllStyles(ITheme theme) {
		String id = theme.getId();
		String idWithoutVersion = null;
		if (theme instanceof Theme) {
			Theme th = (Theme) theme;
			String osVersion = th.getOsVersion();
			if (osVersion != null && osVersion.length() > 0 && id.endsWith(osVersion)) {
				idWithoutVersion = id.substring(0, id.length() - osVersion.length());
			}
		}
