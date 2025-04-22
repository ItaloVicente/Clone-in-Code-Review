	ModifyListener modifyListener = new ModifyListener() {
		@Override
		public void modifyText(ModifyEvent e) {
			doApplyStyles();
		}
	};
