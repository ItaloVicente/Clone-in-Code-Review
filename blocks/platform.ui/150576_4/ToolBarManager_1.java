			for (ToolItem toolItem : toolBar.getItems()) {
				Object data = toolItem.getData();
				if (data instanceof IContributionItem) {
					IContributionItem contributionItem = (IContributionItem) data;
					contributionItem.update();
				}
			}

