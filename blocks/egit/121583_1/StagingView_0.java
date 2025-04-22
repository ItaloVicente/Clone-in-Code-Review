		gitmojiAction = new Action(UIText.StagingView_Gitmoji,
				IAction.AS_CHECK_BOX) {
			@Override
			public void run() {
				gitmoji();
			}
		};
		gitmojiAction.setImageDescriptor(UIIcons.GITMOJI);
		commitMessageToolBarManager.add(gitmojiAction);

