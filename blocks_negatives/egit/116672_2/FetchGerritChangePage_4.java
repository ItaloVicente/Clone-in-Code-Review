			job = new InterruptibleJob(MessageFormat.format(
					UIText.FetchGerritChangePage_FetchingRemoteRefsMessage,
					uriText)) {

				@Override
				protected IStatus run(IProgressMonitor monitor) {
					try {
						listOp.run(monitor);
					} catch (InterruptedException e) {
						return Status.CANCEL_STATUS;
					} catch (InvocationTargetException e) {
						synchronized (ChangeList.this) {
							if (state == State.CANCELING
									|| state == State.INTERRUPT) {
								return Status.CANCEL_STATUS;
							}
						}
						return Activator
								.createErrorStatus(e.getLocalizedMessage(), e);
					}
					List<Change> changes = new ArrayList<>();
					for (Ref ref : listOp.getRemoteRefs()) {
						Change change = Change.fromRef(ref.getName());
						if (change != null) {
							changes.add(change);
						}
					}
					Collections.sort(changes, Collections.reverseOrder());
					result = new LinkedHashSet<>(changes);
					return Status.OK_STATUS;
				}

			};
			job.addJobChangeListener(new JobChangeAdapter() {

				@Override
				public void done(IJobChangeEvent event) {
					IStatus status = event.getResult();
					finish(status != null && status.isOK());
				}

			});
			job.setUser(false);
			job.setSystem(true);
			state = State.SCHEDULED;
			job.schedule();
