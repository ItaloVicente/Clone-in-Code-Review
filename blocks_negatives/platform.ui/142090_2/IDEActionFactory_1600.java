            if (window == null) {
                throw new IllegalArgumentException();
            }
            RetargetAction action = new RetargetAction(getId(), IDEWorkbenchMessages.CloseResourceAction_text);
            action.setToolTipText(IDEWorkbenchMessages.CloseResourceAction_text);
            window.getPartService().addPartListener(action);
            action.setActionDefinitionId(getCommandId());
            return action;
        }
    };

    /**
     * IDE-specific workbench action (id: "closeUnrelatedProjects", commandId: "org.eclipse.ui.project.closeUnrelatedProjects"):
     * Close unrelated projects.
     * <p>
     * This action closes all projects that are unrelated to the selected projects. A
     * project is unrelated if it is not directly or transitively referenced by one
     * of the selected projects, and does not directly or transitively reference
     * one of the selected projects.
     * </p>
     * This action is a {@link RetargetAction} with
     * id "closeUnrelatedProjects". This action maintains its enablement state.
     * @see IProject#getReferencedProjects()
     * @see IProject#getReferencingProjects()
     * @see IProject#close(org.eclipse.core.runtime.IProgressMonitor)
     * @since 3.2
     */
    public static final ActionFactory CLOSE_UNRELATED_PROJECTS = new ActionFactory(
            "closeUnrelatedProjects", IWorkbenchCommandConstants.PROJECT_CLOSE_UNRELATED_PROJECTS) { //$NON-NLS-1$

        @Override
