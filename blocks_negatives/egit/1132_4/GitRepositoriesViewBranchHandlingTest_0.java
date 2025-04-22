			List<String> children = item.getNodes();
			assertEquals("Wrong number of local children", 2, children.size());

			item.getNode(0).select();
			ContextMenuHelper.clickContextMenu(tree, myUtil
					.getPluginLocalizedValue("CheckoutCommand"));
			refreshAndWait();
			touchAndSubmit();
