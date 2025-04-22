	/**
	 * This method must not be called outside the workbench.
	 *
	 * Utility method for creating status.
	 * @param children
	 * @param message
	 * @param exception
	 * @return {@link IStatus}
	 */
	public static IStatus newStatus(List children, String message,
			Throwable exception) {

		List flatStatusCollection = new ArrayList();
		Iterator iter = children.iterator();
		while (iter.hasNext()) {
			IStatus currentStatus = (IStatus) iter.next();
			Iterator childrenIter = flatten(currentStatus).iterator();
			while (childrenIter.hasNext()) {
				flatStatusCollection.add(childrenIter.next());
			}
		}

		IStatus[] stati = new IStatus[flatStatusCollection.size()];
		flatStatusCollection.toArray(stati);
		return newStatus(stati, message, exception);
	}

