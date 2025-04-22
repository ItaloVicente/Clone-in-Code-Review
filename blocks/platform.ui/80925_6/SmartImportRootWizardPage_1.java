		this.proposalSelectionDecorator = new ControlDecoration(tree.getTree(), SWT.TOP | SWT.LEFT);
		Image errorImage = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
				.getImage();
		treeGridData.horizontalIndent += errorImage.getBounds().width;
		this.proposalSelectionDecorator.setImage(errorImage);
		this.proposalSelectionDecorator
				.setDescriptionText(DataTransferMessages.SmartImportWizardPage_selectAtLeastOneFolderToOpenAsProject);
		this.proposalSelectionDecorator.hide();

