			} else {
				selectAppropriateFolderContents((IContainer) resource);
			}
		}
	}

	public void setExportAllTypesValue(boolean value) {
		if (exportAllTypesRadio == null) {
			initialExportAllTypesValue = value;
			exportAllResourcesPreSet = true;
		} else {
			exportAllTypesRadio.setSelection(value);
			exportSpecifiedTypesRadio.setSelection(!value);
		}
	}

	public void setResourceFieldValue(String value) {
		if (resourceNameField == null) {
