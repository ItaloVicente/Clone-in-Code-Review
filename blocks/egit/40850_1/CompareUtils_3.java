			@Override
			public IStatus run(IProgressMonitor monitor) {
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				final ITypedElement left;
				final ITypedElement right;
				try {
					left = getTypedElementFor(repository, leftGitPath, leftRev);
					right = getTypedElementFor(repository, rightGitPath,
							rightRev);
				} catch (IOException e) {
					return Activator.createErrorStatus(
							UIText.CompareWithRefAction_errorOnSynchronize, e);
				}
				final ITypedElement commonAncestor;
				if (left != null && right != null
						&& !GitFileRevision.INDEX.equals(leftRev)
						&& !GitFileRevision.INDEX.equals(rightRev)) {
					commonAncestor = getTypedElementForCommonAncestor(
							repository, rightGitPath, leftRev, rightRev);
				} else {
					commonAncestor = null;
				}

				final GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						left, right, commonAncestor, null);
				in.getCompareConfiguration().setLeftLabel(leftRev);
				in.getCompareConfiguration().setRightLabel(rightRev);
				if (monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				openCompareEditorRunnable(page, in);
				return Status.OK_STATUS;
			}
		};
		job.setUser(true);
		job.schedule();
