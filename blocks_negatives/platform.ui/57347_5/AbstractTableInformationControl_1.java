		fFilterText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				String text = ((Text) e.widget).getText();
				setMatcherString(text);
			}
