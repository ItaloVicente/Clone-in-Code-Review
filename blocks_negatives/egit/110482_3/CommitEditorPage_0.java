
				final ScrolledForm form = getManagedForm().getForm();
				if (UIUtils.isUsable(form))
					form.getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {
							if (!UIUtils.isUsable(form))
								return;

							fillTags(getManagedForm().getToolkit(), tags);
							fillDiffs(diffs);
							fillBranches(branches);
							form.reflow(true);
							form.layout(true, true);
						}
					});

