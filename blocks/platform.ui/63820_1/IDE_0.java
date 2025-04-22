		if (strategy instanceof AskUserViaPopupUnknownEditorStrategy) {
			if (((AskUserViaPopupUnknownEditorStrategy) strategy).isUserCancelled()) {
				throw new PartInitException(IDEWorkbenchMessages.IDE_noFileEditorChoose);
			}
		}

