			final ToolBarContributionItem toolBarContributionItem = new ToolBarContributionItem(
					manager, model.getElementId()) {
				@Override
				public void setVisible(boolean visible) {
					super.setVisible(visible);
					model.setVisible(visible);
				}
			};
