		unstageAction = new Action(UIText.StagingView_UnstageItemMenuLabel,
				UIIcons.UNSTAGE) {
			@Override
			public void run() {
				unstage((IStructuredSelection) stagedViewer.getSelection());
			}
		};
		unstageAction.setToolTipText(UIText.StagingView_UnstageItemTooltip);
		stageAction = new Action(UIText.StagingView_StageItemMenuLabel,
				UIIcons.ELCL16_ADD) {
			@Override
			public void run() {
				stage((IStructuredSelection) unstagedViewer.getSelection());
			}
		};
		stageAction.setToolTipText(UIText.StagingView_StageItemTooltip);

		unstageAction.setEnabled(false);
		stageAction.setEnabled(false);

