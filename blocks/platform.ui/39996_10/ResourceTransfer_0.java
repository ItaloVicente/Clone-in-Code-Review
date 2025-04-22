			if (count > MAX_RESOURCES_TO_TRANSFER) {
				String message = "Transfer aborted, too many resources: " + count; //$NON-NLS-1$
				IDEWorkbenchPlugin.log(message, new IllegalArgumentException(
						"Maximum limit of resources to transfer is: " + MAX_RESOURCES_TO_TRANSFER)); //$NON-NLS-1$
				return null;
			}
