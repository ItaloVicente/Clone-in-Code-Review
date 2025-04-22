		IRunnableWithProgress runnable = pm -> {
try {
		runCommand(pm);
} catch (ExecutionException e) {
		if (pruning) {
			flush();
		}
		throw new InvocationTargetException(e);
}
};
