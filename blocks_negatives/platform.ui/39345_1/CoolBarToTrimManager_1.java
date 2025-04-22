					final ToolBarContributionItem toolBarContributionItem = new ToolBarContributionItem(
							manager, tb.getElementId()) {
						@Override
						public void setVisible(boolean visible) {
							super.setVisible(visible);
							tb.setVisible(visible);
						}
					};
