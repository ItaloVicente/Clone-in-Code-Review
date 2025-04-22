        IWorkspaceDescription description = getWorkspaceDescription();
        description.setFileStateLongevity(longevityValue * DAY_LENGTH);
        description.setMaxFileStates(maxFileStates);
        description.setMaxFileStateSize(maxStateSize * MEGABYTES);
        description.setApplyFileStatePolicy(applyPolicy);

        try {
            ResourcesPlugin.getWorkspace().setDescription(description);
        } catch (CoreException exception) {
            ErrorDialog.openError(getShell(), IDEWorkbenchMessages.FileHistory_exceptionSaving, exception
                    .getMessage(), exception.getStatus());
            return false;
        }
