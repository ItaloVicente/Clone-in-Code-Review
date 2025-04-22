			} else {
				ITypedElement base = new LocalNonWorkspaceTypedElement(
						new Path(getRepository().getWorkTree()
								.getAbsolutePath()).append(p));

				final ITypedElement right;
				if (d.getChange().equals(ChangeType.DELETE))
					right = new GitCompareFileRevisionEditorInput.EmptyTypedElement(
							""); //$NON-NLS-1$
				else
					right = CompareUtils.getFileRevisionTypedElement(p, commit,
							getRepository(), blobs[blobs.length - 1]);

				final ITypedElement commonAncestor;
				if (commit != null) {
					final ObjectId headCommitId = getRepository().resolve(
							Constants.HEAD);
					commonAncestor = CompareUtils
							.getFileRevisionTypedElementForCommonAncestor(p,
									headCommitId, commit, getRepository());
				} else {
					commonAncestor = null;
				}

				GitCompareFileRevisionEditorInput in = new GitCompareFileRevisionEditorInput(
						base, right, commonAncestor, null);
				CompareUtils.openInCompare(site.getWorkbenchWindow()
						.getActivePage(), in);
