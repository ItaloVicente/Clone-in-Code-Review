
	public static boolean confirmCheckout(Shell shell, Repository repository) {
		return confirmCheckout(shell,
				Collections.singletonMap(repository, Collections.emptyList()),
				true);
	}

	public static boolean confirmCheckout(Shell shell,
			Map<Repository, Collection<String>> repoAndPaths,
			boolean filesOnly) {
		if (haveChanges(repoAndPaths, filesOnly)) {
			String question = UIText.DiscardChangesAction_confirmActionMessage;
			String launch = LaunchFinder
					.getRunningLaunchConfiguration(repoAndPaths.keySet(), null);
			if (launch != null) {
				question = MessageFormat.format(question,
						"\n\n" + MessageFormat.format( //$NON-NLS-1$
								UIText.LaunchFinder_RunningLaunchMessage,
								launch));
			} else {
				question = MessageFormat.format(question, ""); //$NON-NLS-1$
			}
			Shell parent = shell != null ? shell
					: PlatformUI.getWorkbench().getModalDialogShellProvider()
							.getShell();
			MessageDialog messageDialog = new MessageDialog(parent,
					UIText.DiscardChangesAction_confirmActionTitle, null,
					question, MessageDialog.CONFIRM,
					new String[] {
							UIText.DiscardChangesAction_discardChangesButtonText,
							IDialogConstants.CANCEL_LABEL },
					0);
			return messageDialog.open() == Window.OK;
		} else {
			return !LaunchFinder.shouldCancelBecauseOfRunningLaunches(
					repoAndPaths.keySet(), null);
		}
	}

	private static boolean haveChanges(
			Map<Repository, Collection<String>> paths,
			boolean filesOnly) {
		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		for (Map.Entry<Repository, Collection<String>> entry : paths
				.entrySet()) {
			Repository repo = entry.getKey();
			Assert.isNotNull(repo);
			IndexDiffCacheEntry indexDiff = cache.getIndexDiffCacheEntry(repo);
			if (indexDiff == null) {
				return true; // No info, assume worst case
			}
			IndexDiffData diff = indexDiff.getIndexDiff();
			if (diff == null || hasChanges(diff, entry.getValue(), filesOnly)) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasChanges(@NonNull IndexDiffData diff,
			Collection<String> paths, boolean filesOnly) {
		if (paths.isEmpty()) {
			return diff.hasChanges();
		}
		Set<String> repoPaths = new HashSet<>(paths);
		if (repoPaths.contains("")) { //$NON-NLS-1$
			return diff.hasChanges();
		}
		if (containsAny(repoPaths, diff.getAdded())
				|| containsAny(repoPaths, diff.getChanged())
				|| containsAny(repoPaths, diff.getModified())
				|| containsAny(repoPaths, diff.getRemoved())) {
			return true;
		}
		if (!filesOnly) {
			return containsAnyDirectory(repoPaths, diff.getAdded())
					|| containsAnyDirectory(repoPaths, diff.getChanged())
					|| containsAnyDirectory(repoPaths, diff.getModified())
					|| containsAnyDirectory(repoPaths, diff.getRemoved());
		}
		return false;
	}

	private static boolean containsAny(Set<String> repoPaths,
			Collection<String> files) {
		return files.stream().anyMatch(repoPaths::contains);
	}

	private static boolean containsAnyDirectory(Set<String> repoPaths,
			Collection<String> files) {
		String lastDirectory = null;
		for (String file : files) {
			int j = file.lastIndexOf('/');
			if (j <= 0) {
				continue;
			}
			String directory = file.substring(0, j);
			String withTerminator = directory + '/';
			if (lastDirectory != null
					&& lastDirectory.startsWith(withTerminator)) {
				continue;
			}
			if (repoPaths.contains(directory)) {
				return true;
			}
			lastDirectory = withTerminator;
			for (int i = directory.indexOf('/'); i > 0; i = directory
					.indexOf('/', i + 1)) {
				if (repoPaths.contains(directory.substring(0, i))) {
					return true;
				}
			}
		}
		return false;
	}
