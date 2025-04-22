	private void setBuildType(int type) {
		switch (type) {
		case IncrementalProjectBuilder.INCREMENTAL_BUILD:
		case IncrementalProjectBuilder.AUTO_BUILD:
			setText(IDEWorkbenchMessages.GlobalBuildAction_text);
			setToolTipText(IDEWorkbenchMessages.GlobalBuildAction_toolTip);
			setId("build"); //$NON-NLS-1$
			workbenchWindow.getWorkbench().getHelpSystem().setHelp(this,
					IIDEHelpContextIds.GLOBAL_INCREMENTAL_BUILD_ACTION);
			setImageDescriptor(IDEInternalWorkbenchImages
					.getImageDescriptor(IDEInternalWorkbenchImages.IMG_ETOOL_BUILD_EXEC));
			setDisabledImageDescriptor(IDEInternalWorkbenchImages
					.getImageDescriptor(IDEInternalWorkbenchImages.IMG_ETOOL_BUILD_EXEC_DISABLED));
			setActionDefinitionId(IWorkbenchCommandConstants.PROJECT_BUILD_ALL);
			break;
		case IncrementalProjectBuilder.FULL_BUILD:
			setText(IDEWorkbenchMessages.GlobalBuildAction_rebuildText);
			setToolTipText(IDEWorkbenchMessages.GlobalBuildAction_rebuildToolTip);
			setId("rebuildAll"); //$NON-NLS-1$
			workbenchWindow.getWorkbench().getHelpSystem().setHelp(this,
					IIDEHelpContextIds.GLOBAL_FULL_BUILD_ACTION);
			setActionDefinitionId("org.eclipse.ui.project.rebuildAll"); //$NON-NLS-1$
			break;
		default:
			throw new IllegalArgumentException("Invalid build type"); //$NON-NLS-1$
		}
		this.buildType = type;
	}
