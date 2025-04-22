				editedWorkingSet.setElements(originalWorkingSet.getElements());
			}
		}
	}

	private void restoreRemovedWorkingSets() {
		IWorkingSetManager manager = WorkbenchPlugin.getDefault().getWorkingSetManager();
		Iterator iterator = getRemovedWorkingSets().iterator();

		while (iterator.hasNext()) {
			manager.addWorkingSet(((IWorkingSet) iterator.next()));
		}
		iterator = getRemovedMRUWorkingSets().iterator();
		while (iterator.hasNext()) {
			manager.addRecentWorkingSet(((IWorkingSet) iterator.next()));
		}
	}

	@Override
