					GridData gridData = (GridData) errorsTable.getControl().getLayoutData();
					if (gridData.exclude) {
						gridData.exclude = false;
						((GridData) errorsLabel.getLayoutData()).exclude = false;
					}
					errorsTable.refresh();
					errorsTable.getTable().update();
					errorsLabel.setText(
							NLS.bind(DataTransferMessages.SmartImportReport_importErrors, job.getErrors().size()));
					res.layout(true);
