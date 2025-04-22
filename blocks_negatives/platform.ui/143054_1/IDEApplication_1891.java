            force = true;

            if (instancePath.length() <= 0) {
                MessageDialog
                .openError(
                        shell,
                        IDEWorkbenchMessages.IDEApplication_workspaceEmptyTitle,
                        IDEWorkbenchMessages.IDEApplication_workspaceEmptyMessage);
                continue;
            }

            File workspace = new File(instancePath);
            if (!workspace.exists()) {
