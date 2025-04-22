		textWidget.setMenu(contextMenu.createContextMenu(textWidget));

		textWidget.addFocusListener(new FocusListener() {

			private IHandlerActivation cutHandlerActivation;
			private IHandlerActivation copyHandlerActivation;
			private IHandlerActivation pasteHandlerActivation;
			private IHandlerActivation selectAllHandlerActivation;
			private IHandlerActivation undoHandlerActivation;
			private IHandlerActivation redoHandlerActivation;
			private IHandlerActivation quickFixHandlerActivation;
			private IHandlerActivation contentAssistHandlerActivation;

			@Override
			public void focusGained(FocusEvent e) {
				IHandlerService service = getHandlerService();
				if (service == null)
					return;

				if (cutAction != null) {
					cutAction.update();
					cutHandlerActivation = service.activateHandler(
							IWorkbenchCommandConstants.EDIT_CUT,
							new ActionHandler(cutAction),
							new ActiveShellExpression(getParent().getShell()));
				}
				copyAction.update();

				copyHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_COPY,
						new ActionHandler(copyAction),
						new ActiveShellExpression(getParent().getShell()));
				if (pasteAction != null)
					this.pasteHandlerActivation = service.activateHandler(
							IWorkbenchCommandConstants.EDIT_PASTE,
							new ActionHandler(pasteAction),
							new ActiveShellExpression(getParent().getShell()));
				selectAllHandlerActivation = service.activateHandler(
						IWorkbenchCommandConstants.EDIT_SELECT_ALL,
						new ActionHandler(selectAllAction),
						new ActiveShellExpression(getParent().getShell()));
				if (undoAction != null)
					undoHandlerActivation = service.activateHandler(
							IWorkbenchCommandConstants.EDIT_UNDO,
							new ActionHandler(undoAction),
							new ActiveShellExpression(getParent().getShell()));
				if (redoAction != null)
					redoHandlerActivation = service.activateHandler(
							IWorkbenchCommandConstants.EDIT_REDO,
							new ActionHandler(redoAction),
							new ActiveShellExpression(getParent().getShell()));
				if (quickFixActionHandler != null)
					quickFixHandlerActivation = getHandlerService().activateHandler(
							quickFixActionHandler.getAction().getActionDefinitionId(),
							quickFixActionHandler,
							new ActiveShellExpression(getParent().getShell()));
				if (contentAssistActionHandler != null)
					contentAssistHandlerActivation = getHandlerService().activateHandler(
							contentAssistActionHandler.getAction().getActionDefinitionId(),
							contentAssistActionHandler,
							new ActiveShellExpression(getParent().getShell()));
			}

			@Override
			public void focusLost(FocusEvent e) {
				IHandlerService service = getHandlerService();
				if (service == null)
					return;

				if (cutHandlerActivation != null)
					service.deactivateHandler(cutHandlerActivation);

				if (copyHandlerActivation != null)
					service.deactivateHandler(copyHandlerActivation);

				if (pasteHandlerActivation != null)
					service.deactivateHandler(pasteHandlerActivation);

				if (selectAllHandlerActivation != null)
					service.deactivateHandler(selectAllHandlerActivation);

				if (undoHandlerActivation != null)
					service.deactivateHandler(undoHandlerActivation);

				if (redoHandlerActivation != null)
					service.deactivateHandler(redoHandlerActivation);

				if (quickFixHandlerActivation != null)
					service.deactivateHandler(quickFixHandlerActivation);

				if (contentAssistHandlerActivation != null)
					service.deactivateHandler(contentAssistHandlerActivation);
			}
