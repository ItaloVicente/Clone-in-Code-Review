			stageAllAction
					.setEnabled(unstagedViewer.getTree().getItemCount() > 0);
			unstagedToolBarManager.update(true);
			unstageAllAction
					.setEnabled(stagedViewer.getTree().getItemCount() > 0);
			stagedToolBarManager.update(true);
