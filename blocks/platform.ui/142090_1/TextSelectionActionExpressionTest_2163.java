		}
		fail("Unable to find menu manager");
		return null;
	}

	private void testAction(MenuManager mgr, String action, boolean expected)
			throws Throwable {
		assertEquals(action, expected, ActionUtil.getActionWithLabel(mgr,
				action).isEnabled());
	}
