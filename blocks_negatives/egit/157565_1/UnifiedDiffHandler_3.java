						input.setDocument(job.getDocument());
						new UIJob(UIText.DiffEditor_TaskUpdatingViewer) {

							@Override
							public IStatus runInUIThread(
									IProgressMonitor uiMonitor) {
								try {
									page.openEditor(input, DiffEditor.EDITOR_ID,
											true);
								} catch (PartInitException e) {
									return e.getStatus();
								}
								return Status.OK_STATUS;
							}
						}.schedule(50);
