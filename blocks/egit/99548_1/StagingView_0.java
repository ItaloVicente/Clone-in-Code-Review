		unstageAllAction = new Action(
				UIText.StagingView_UnstageAllItemMenuLabel,
				UIIcons.UNSTAGE_ALL) {
			@Override
			public void run() {
				stagedViewer.getTree().selectAll();
				unstage((IStructuredSelection) stagedViewer.getSelection());
			}
		};
		unstageAllAction
				.setToolTipText(UIText.StagingView_UnstageAllItemTooltip);
		stageAllAction = new Action(UIText.StagingView_StageAllItemMenuLabel,
				UIIcons.ELCL16_ADD_ALL) {
			@Override
			public void run() {
				unstagedViewer.getTree().selectAll();
				stage((IStructuredSelection) unstagedViewer.getSelection());
			}
		};
		stageAllAction.setToolTipText(UIText.StagingView_StageAllItemTooltip);

		unstageAllAction.setEnabled(false);
		stageAllAction.setEnabled(false);

