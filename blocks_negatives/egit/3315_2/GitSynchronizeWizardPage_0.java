		treeViewer.addCheckStateListener(new ICheckStateListener() {
			public void checkStateChanged(CheckStateChangedEvent event) {
				Repository element = (Repository) event.getElement();
				if (event.getChecked()) {
					SyncData data = repoMapping.get(element);
					data.srcRev = ((CCombo) srcBranchesEditor.getControl())
							.getText();
					data.dstRev = ((CCombo) dstBranchesEditor.getControl())
							.getText();
					Boolean includeLocal = (Boolean) includeLocalEditor.getValue();
					data.includeLocal = includeLocal == null ? false : includeLocal.booleanValue();
					repoMapping.put(element, data);
				} else {
					SyncData data = repoMapping.get(element);
					data.srcRev = data.srcRev = null;
					repoMapping.put(element, data);
				}

				boolean isPageCompleted = true;
				for (Object checked : treeViewer.getCheckedElements()) {
					SyncData data = repoMapping.get(checked);
					if (data.srcRev == null || data.dstRev == null) {
						isPageCompleted = false;
						break;
					}
				}
				setPageComplete(isPageCompleted);
			}
		});

