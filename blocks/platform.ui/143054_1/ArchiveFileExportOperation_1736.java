			} catch (CoreException e) {
			}
			monitor.beginTask(DataTransferMessages.DataTransfer_exportingTitle, totalWork);
			if (resourcesToExport == null) {
				exportResource(resource);
			} else {
				exportSpecifiedResources();
			}

			try {
				exporter.finished();
			} catch (IOException e) {
				throw new InvocationTargetException(
						e,
						NLS.bind(DataTransferMessages.ZipExport_cannotClose, e.getMessage()));
			}
		} finally {
			monitor.done();
		}
	}

	public void setCreateLeadupStructure(boolean value) {
		createLeadupStructure = value;
	}

	public void setUseCompression(boolean value) {
		useCompression = value;
	}

	public void setUseTarFormat(boolean value) {
		useTarFormat = value;
	}
