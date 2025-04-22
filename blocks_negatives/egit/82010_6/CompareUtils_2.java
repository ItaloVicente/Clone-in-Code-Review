
	/**
	 * Indicates if it is OK to open the selected file directly in a compare
	 * editor.
	 * <p>
	 * It is not OK to show the single file if the file is part of a
	 * logical model element that spans multiple files.
	 * </p>
	 *
	 * @param file
	 *            file the user is trying to compare
	 * @return <code>true</code> if the file can be opened directly in a compare
	 *         editor, <code>false</code> if the synchronize view should be
	 *         opened instead.
	 */
	public static boolean canDirectlyOpenInCompare(@NonNull IFile file) {
		/*
		 * Note : it would be better to use a remote context here in order to
		 * give the model provider a chance to resolve the remote logical model
		 * instead of only relying on the local one. However, this might be a
		 * long operation and would not really provide more context : we're
		 * trying to determine if the local file can be compared alone, this can
		 * be done by relying on the local model only.
		 */
		if (Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.USE_LOGICAL_MODEL)) {

			final ResourceMapping[] mappings = ResourceUtil
					.getResourceMappings(file,
							ResourceMappingContext.LOCAL_CONTEXT);

			for (ResourceMapping mapping : mappings) {
				try {
					final ResourceTraversal[] traversals = mapping
							.getTraversals(
									ResourceMappingContext.LOCAL_CONTEXT, null);
					for (ResourceTraversal traversal : traversals) {
						final IResource[] resources = traversal.getResources();
						for (IResource resource : resources) {
							if (!resource.equals(file))
								return false;
						}
					}
				} catch (CoreException e) {
					Activator.logError(e.getMessage(), e);
				}
			}

		}
		return true;
	}
