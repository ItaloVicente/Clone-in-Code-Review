					return true;
				}
				return false;
			}
		};
	}

	protected File getSourceDirectory() {
		return getSourceDirectory(this.sourceNameField.getText());
	}

	private File getSourceDirectory(String path) {
		File sourceDirectory = new File(getSourceDirectoryName(path));
		if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) {
			return null;
		}

		return sourceDirectory;
	}

	private String getSourceDirectoryName() {
		return getSourceDirectoryName(this.sourceNameField.getText());
	}

	private String getSourceDirectoryName(String sourceName) {
		IPath result = new Path(sourceName.trim());

		if (result.getDevice() != null && result.segmentCount() == 0) {
