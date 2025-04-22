	private boolean executePushCommand(ISelection selection)
			throws ExecutionException {
		if (!(selection instanceof IStructuredSelection)) {
			throw new ExecutionException(NLS.bind(
					UIText.CommitActionHandler_NoSelection,
					EGIT_PUSH_COMMAND_ID));
		}
		IStructuredSelection structuredSelection = (IStructuredSelection) selection;
		try {
			return CommandUtils.executeCommand(EGIT_PUSH_COMMAND_ID,
					structuredSelection);
		} catch (NotDefinedException e) {
			throw new ExecutionException(NLS.bind(
					UIText.CommitActionHandler_CommandNotDefined,
					EGIT_PUSH_COMMAND_ID), e);
		} catch (NotEnabledException e) {
			throw new ExecutionException(NLS.bind(
					UIText.CommitActionHandler_CommandNotEnabled,
					EGIT_PUSH_COMMAND_ID), e);
		} catch (NotHandledException e) {
			throw new ExecutionException(NLS.bind(
					UIText.CommitActionHandler_CommandNotHandled,
					EGIT_PUSH_COMMAND_ID), e);
		}
	}

