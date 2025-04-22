		case 0: {
			SWTBotMenu checkedChild = filterMenu
					.menu(new BaseMatcher<MenuItem>() {

						@Override
						public boolean matches(Object item) {
							return item instanceof MenuItem
									&& ((MenuItem) item).getSelection();
						}

						@Override
						public void describeTo(Description description) {
							description.appendText("Checked menu item");

						}
					}, true, 0);
			if (checkedChild != null) {
				checkedChild.click();
			}
