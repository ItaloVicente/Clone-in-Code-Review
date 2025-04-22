		IRunnableWithProgress op = monitor -> {
CreateProjectOperation op1 = new CreateProjectOperation(
			description, ResourceMessages.NewProject_windowTitle);
try {
		op1.execute(monitor, WorkspaceUndoUtil
			.getUIInfoAdapter(getShell()));
} catch (ExecutionException e) {
		throw new InvocationTargetException(e);
}
};
