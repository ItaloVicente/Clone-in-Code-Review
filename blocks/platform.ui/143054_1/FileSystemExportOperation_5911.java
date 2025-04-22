			} catch (CoreException e) {
				errorTable.add(e.getStatus());
			}
			monitor.beginTask(DataTransferMessages.DataTransfer_exportingTitle, totalWork);
			if (resourcesToExport == null) {
				exportAllResources();
			} else {
				exportSpecifiedResources();
			}
		} finally {
			monitor.done();
		}
	}

	public void setCreateContainerDirectories(boolean value) {
		createContainerDirectories = value;
	}

	public void setCreateLeadupStructure(boolean value) {
		createLeadupStructure = value;
	}

	public void setOverwriteFiles(boolean value) {
		if (value) {
