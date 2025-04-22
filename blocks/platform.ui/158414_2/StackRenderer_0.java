		for (int i = 0; i < 6; i++) {
			ToolItem ti2 = new ToolItem(menuTB, SWT.PUSH);
			if(i%2==0) {
				new ToolItem(menuTB, SWT.SEPARATOR);
			}
			ti2.setImage(getViewMenuImage());
			ti2.setHotImage(null);
			ti2.setToolTipText(SWTRenderersMessages.viewMenu + i);

		}
