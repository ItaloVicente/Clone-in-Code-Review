					elements.add(file);
					selectionMap.put(parent, elements);
				}
			}

		};

		IRunnableWithProgress runnable = monitor -> {
			monitor
			.beginTask(
					DataTransferMessages.ImportPage_filterSelections, IProgressMonitor.UNKNOWN);
			getSelectedResources(filter, monitor);
		};

		try {
			dialog.run(true, true, runnable);
		} catch (InvocationTargetException exception) {
			return;
		} catch (InterruptedException exception) {
			return;
		}
		getShell().update();
		if (selectionMap != null) {
			updateSelections(selectionMap);
		}
	}

	@Override
