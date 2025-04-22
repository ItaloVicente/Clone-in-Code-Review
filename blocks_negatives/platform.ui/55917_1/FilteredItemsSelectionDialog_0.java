				IProgressMonitor subMonitor = null;
				int reportEvery = lastFilteredItems.size() / 20;
				if (monitor != null) {
					subMonitor = new SubProgressMonitor(monitor, 100);
					subMonitor
							.beginTask(
									WorkbenchMessages.FilteredItemsSelectionDialog_cacheRefreshJob_checkDuplicates,
									5);
				}
				HashMap helperMap = new HashMap();
