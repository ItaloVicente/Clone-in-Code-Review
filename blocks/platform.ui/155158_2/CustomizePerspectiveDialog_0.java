			ImageDescriptor iconDescriptor;
			DisplayItem menuEntry;
			if (OpaqueElementUtil.isOpaqueMenu(menuItem)) {
				if (processedOpaqueItems.containsKey(manager)) {
					menuEntry = processedOpaqueItems.get(manager);
				} else {
					menuEntry = new DisplayItem(manager.getMenuText(), manager);
					parent.addChild(menuEntry);
