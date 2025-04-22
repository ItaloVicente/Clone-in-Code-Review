		addContribution(additions, new TestAction(TEST_ITEM_WITHOUT_VISIBLE_WHEN), null);
		addContribution(additions, new TestAction(TEST_ITEM_WITH_ALWAYS_TRUE_VISIBLE_WHEN), AlwaysEnabledExpression.INSTANCE);
		addContribution(additions, new TestAction(TEST_ITEM_WITH_ALWAYS_FALSE_VISIBLE_WHEN), new AlwaysDisabledExpression());

		additions.addContributionItem(new TestActionMenuManager(TEST_MENU_MANAGER_WITHOUT_VISIBLE_WHEN), null);
		additions.addContributionItem(new TestActionMenuManager(TEST_MENU_MANAGER_WITH_ALWAYS_TRUE_VISIBLE_WHEN), AlwaysEnabledExpression.INSTANCE);
		additions.addContributionItem(new TestActionMenuManager(TEST_MENU_MANAGER_WITH_ALWAYS_FALSE_VISIBLE_WHEN), new AlwaysDisabledExpression());
	}

	private void addContribution(IContributionRoot additions, Action action, Expression visibleWhen) {
		additions.addContributionItem(new ActionContributionItem(action), visibleWhen);
	}

	private static class TestAction extends Action {
		public TestAction(String id) {
			super(id);
			setId(id);
		}
	}

	private static class TestActionMenuManager extends MenuManager {
		private ActionContributionItem actionContributionItem;

		public TestActionMenuManager(String id) {
			super(id, id);
			actionContributionItem = new ActionContributionItem(new TestAction(id));
			add(new TestAction(TEST_ITEM_WITHOUT_VISIBLE_WHEN));
		}

		@Override
