            IWorkspaceRoot root = IDEWorkbenchPlugin.getPluginWorkspace()
                    .getRoot();
            IContainer container = (IContainer) root.findMember(destination);
            if (container == null) {
                return;
            }
