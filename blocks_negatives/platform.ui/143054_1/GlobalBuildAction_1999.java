    /**
     * Sets the build type.
     *
     * @param type
     *            the type of build; one of
     *            <code>IncrementalProjectBuilder.INCREMENTAL_BUILD</code> or
     *            <code>IncrementalProjectBuilder.FULL_BUILD</code>
     */
    private void setBuildType(int type) {
        switch (type) {
        case IncrementalProjectBuilder.INCREMENTAL_BUILD:
        case IncrementalProjectBuilder.AUTO_BUILD:
            setText(IDEWorkbenchMessages.GlobalBuildAction_text);
            setToolTipText(IDEWorkbenchMessages.GlobalBuildAction_toolTip);
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
            workbenchWindow.getWorkbench().getHelpSystem().setHelp(this,
                    IIDEHelpContextIds.GLOBAL_FULL_BUILD_ACTION);
            break;
        default:
        }
        this.buildType = type;
    }
