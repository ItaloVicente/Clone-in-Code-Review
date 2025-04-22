			for (ToolItem toolItem : toolBar.getItems()) {
				if (newItemsList.contains(toolItem)) {
					continue;
				}
				Object data = toolItem.getData();
				if (data instanceof IContributionItem) {
					IContributionItem contributionItem = (IContributionItem) data;
					contributionItem.update();
				}
			}

