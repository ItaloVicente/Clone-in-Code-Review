	public static void addRebaseCommandFinishListener(
			RebaseCommandFinishedListener listener) {
		if (listeners.contains(listener))
			return;
		listeners.add(listener);
	}
	public static void removeRebaseCommandFinishListener(
			RebaseCommandFinishedListener listener) {
		listeners.remove(listener);
	}

	public interface RebaseCommandFinishedListener {
		void operationFinished(IStatus result, Repository repository, Ref ref,
				Operation operation);
	}

	public static void fireRebaseCommandFinished(IStatus result,
			Repository repository, Ref ref, Operation operation) {
		for (RebaseCommandFinishedListener listener : listeners) {
			listener.operationFinished(result, repository, ref, operation);
		}
	}

