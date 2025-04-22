		menu.addMenuListener(new MenuListener() {

			@Override
			public void menuHidden(MenuEvent e) {
				perspSwitcherToolbar.getDisplay().asyncExec(() -> menu.dispose());
			}

			@Override
			public void menuShown(MenuEvent e) {
			}

		});
