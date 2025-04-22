		linkTargetField.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				linkTarget = linkTargetField.getText();
				if (isDefaultConfigurationSelected()) {
					linkTarget = getPathVariableManager().convertFromUserEditableFormat(linkTarget, true);
				}
				resolveVariable();
				if (updatableResourceName != null) {
					String value = updatableResourceName.getValue();
					if (value == null
						IPath linkTargetPath = new Path(linkTarget);
						String lastSegment = linkTargetPath.lastSegment();
						if (lastSegment != null) {
							lastUpdatedValue = lastSegment;
							updatableResourceName.setValue(lastSegment);
						}
