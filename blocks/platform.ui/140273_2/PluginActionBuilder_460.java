			insertMenuGroup(menu, new GroupMarker(id));
		}

		protected void contributeToolbarAction(ActionDescriptor ad, IToolBarManager toolbar, boolean appendIfMissing) {
			String tId = ad.getToolbarId();
			String tgroup = ad.getToolbarGroupId();
			if (tId == null && tgroup == null) {
