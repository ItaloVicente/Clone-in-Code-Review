		try {
			this.monitor = monitor;
			monitor.beginTask(DataTransferMessages.DataTransfer_scanningMatching, IProgressMonitor.UNKNOWN);
			result = createElement(null, root);
			if (result == null) {
				result = new FileSystemElement(provider.getLabel(root), null,
						provider.isFolder(root));
				result.setFileSystemObject(root);
			}
		} finally {
			monitor.done();
		}
	}

	public void setDesiredExtensions(String[] extensions) {
		desiredExtensions = extensions;
	}
