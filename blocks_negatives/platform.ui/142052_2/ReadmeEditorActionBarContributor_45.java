            if (activeEditor != null)
                editorName = activeEditor.getTitle();
            MessageDialog
                    .openInformation(
                            shell,
                            MessageUtil.getString("Readme_Editor"), //$NON-NLS-1$
                            MessageUtil
                                    .format(
                                            "ReadmeEditorActionExecuted", new Object[] { getText(), editorName })); //$NON-NLS-1$
        }

        public void setActiveEditor(IEditorPart part) {
            activeEditor = part;
        }
    }

    /**
     * Creates a new ReadmeEditorActionBarContributor.
     */
    public ReadmeEditorActionBarContributor() {
        action1
                .setDisabledImageDescriptor(ReadmeImages.EDITOR_ACTION1_IMAGE_DISABLE);
        action1.setImageDescriptor(ReadmeImages.EDITOR_ACTION1_IMAGE_ENABLE);
        action1.setHoverImageDescriptor(ReadmeImages.EDITOR_ACTION1_IMAGE);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(action1, IReadmeConstants.EDITOR_ACTION1_CONTEXT);

        action2 = new RetargetAction(IReadmeConstants.RETARGET2, MessageUtil
        action2
                .setDisabledImageDescriptor(ReadmeImages.EDITOR_ACTION2_IMAGE_DISABLE);
        action2.setImageDescriptor(ReadmeImages.EDITOR_ACTION2_IMAGE_ENABLE);
        action2.setHoverImageDescriptor(ReadmeImages.EDITOR_ACTION2_IMAGE);

        action3 = new LabelRetargetAction(IReadmeConstants.LABELRETARGET3,
        action3
                .setDisabledImageDescriptor(ReadmeImages.EDITOR_ACTION3_IMAGE_DISABLE);
        action3.setImageDescriptor(ReadmeImages.EDITOR_ACTION3_IMAGE_ENABLE);
        action3.setHoverImageDescriptor(ReadmeImages.EDITOR_ACTION3_IMAGE);

        PlatformUI.getWorkbench().getHelpSystem().setHelp(action2, IReadmeConstants.EDITOR_ACTION2_CONTEXT);

        PlatformUI.getWorkbench().getHelpSystem().setHelp(action3, IReadmeConstants.EDITOR_ACTION3_CONTEXT);


        dirtyStateContribution = new DirtyStateContribution();
    }

    @Override
