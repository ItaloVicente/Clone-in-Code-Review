		ContextMenuHelper.clickContextMenu(view.bot().tree(), myUtil
				.getPluginLocalizedValue("CheckoutCommand"));
		TestUtil.joinJobs(JobFamilies.CHECKOUT);

		try {
			ContextMenuHelper.clickContextMenu(view.bot().tree(), myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
		} catch (WidgetNotFoundException e) {
		}

