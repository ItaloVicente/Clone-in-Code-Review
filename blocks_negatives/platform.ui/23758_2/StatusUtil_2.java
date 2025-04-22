	/**
	 * Answer a flat collection of the passed status and its recursive children
	 */
	protected static List flatten(IStatus aStatus) {
		List result = new ArrayList();

		if (aStatus.isMultiStatus()) {
			IStatus[] children = aStatus.getChildren();
			for (int i = 0; i < children.length; i++) {
				IStatus currentChild = children[i];
				if (currentChild.isMultiStatus()) {
					Iterator childStatiiEnum = flatten(currentChild).iterator();
					while (childStatiiEnum.hasNext()) {
						result.add(childStatiiEnum.next());
					}
				} else {
					result.add(currentChild);
				}
			}
		} else {
			result.add(aStatus);
		}

		return result;
	}

	/**
	 * This method must not be called outside the workbench.
	 *
	 * Utility method for creating status.
	 */
	protected static IStatus newStatus(IStatus[] stati, String message,
			Throwable exception) {

		Assert.isTrue(message != null);
	    Assert.isTrue(message.trim().length() != 0);

		return new MultiStatus(IProgressConstants.PLUGIN_ID, IStatus.ERROR,
				stati, message, exception);
	}

