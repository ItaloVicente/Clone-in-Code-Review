
		if (part == this) {
			PageSite pageSite = getPageSite(getCurrentPage());
			if (pageSite != null) {
				IEclipseContext pageContext = pageSite.getSiteContext();
				if (pageContext != null) {
					pageContext.activate();
				}
			}
		}
