				table.getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						checkFocusLost(table, txtQuickAccess);
					}
				});
			}
		});
		txtQuickAccess.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				showList();
