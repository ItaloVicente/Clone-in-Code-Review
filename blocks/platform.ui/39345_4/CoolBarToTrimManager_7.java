	private final class ToolBarContributionItemExtension extends ToolBarContributionItem {
		private final MToolBar tb;

		private ToolBarContributionItemExtension(IToolBarManager toolBarManager, MToolBar tb) {
			super(toolBarManager, tb.getElementId());
			this.tb = tb;
		}

		@Override
		public void setVisible(boolean visible) {
			super.setVisible(visible);
			tb.setVisible(visible);
		}
	}

