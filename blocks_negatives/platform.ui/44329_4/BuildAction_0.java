            setId(ID_BUILD);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                    IIDEHelpContextIds.INCREMENTAL_BUILD_ACTION);
        } else {
            setText(IDEWorkbenchMessages.RebuildAction_text);
            setToolTipText(IDEWorkbenchMessages.RebuildAction_tooltip);
            setId(ID_REBUILD_ALL);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
					IIDEHelpContextIds.FULL_BUILD_ACTION);
