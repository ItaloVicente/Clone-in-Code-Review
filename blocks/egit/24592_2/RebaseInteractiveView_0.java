		planTreeViewer.addDoubleClickListener(new IDoubleClickListener() {

			public void doubleClick(DoubleClickEvent event) {
				PlanElement element = (PlanElement) ((IStructuredSelection) event
						.getSelection()).getFirstElement();
				if (element == null)
					return;

				RepositoryCommit commit = loadCommit(element.getCommit());
				if (commit != null)
					CommitEditor.openQuiet(commit);

			}

			private RepositoryCommit loadCommit(
					AbbreviatedObjectId abbreviatedObjectId) {
				if (abbreviatedObjectId != null) {
					RevWalk walk = new RevWalk(
							RebaseInteractiveView.this.currentRepository);
					try {
						Collection<ObjectId> resolved = walk.getObjectReader()
								.resolve(abbreviatedObjectId);
						if (resolved.size() == 1) {
							RevCommit commit = walk.parseCommit(resolved
									.iterator().next());
							return new RepositoryCommit(
									RebaseInteractiveView.this.currentRepository,
									commit);
						}
					} catch (IOException e) {
						return null;
					} finally {
						walk.release();
					}
				}
				return null;
			}
		});
