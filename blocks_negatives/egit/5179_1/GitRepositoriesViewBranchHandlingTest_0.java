		localItem.getNode(0).select();
		try {
			ContextMenuHelper.clickContextMenu(view.bot().tree(), myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
		} catch (WidgetNotFoundException e1) {
		}
