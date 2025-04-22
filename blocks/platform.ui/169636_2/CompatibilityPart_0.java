			WorkbenchPage page = (WorkbenchPage) reference.getPage();
			if (page == null) {
				return;
			}
			page.firePartDeactivatedIfActive(part);
			page.firePartHidden(part);
			page.firePartClosed(CompatibilityPart.this);
