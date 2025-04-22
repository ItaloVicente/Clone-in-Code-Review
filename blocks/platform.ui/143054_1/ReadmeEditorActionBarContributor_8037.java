			String editorName = MessageUtil.getString("Empty_Editor_Name"); //$NON-NLS-1$
			if (activeEditor != null)
				editorName = activeEditor.getTitle();
			MessageDialog.openInformation(shell, MessageUtil.getString("Readme_Editor"), //$NON-NLS-1$
					MessageUtil.format("ReadmeEditorActionExecuted", new Object[] { getText(), editorName })); //$NON-NLS-1$
		}

		public void setActiveEditor(IEditorPart part) {
			activeEditor = part;
		}
	}

	public ReadmeEditorActionBarContributor() {
		action1 = new EditorAction(MessageUtil.getString("Editor_Action1")); //$NON-NLS-1$
		action1.setToolTipText(MessageUtil.getString("Readme_Editor_Action1")); //$NON-NLS-1$
		action1.setDisabledImageDescriptor(ReadmeImages.EDITOR_ACTION1_IMAGE_DISABLE);
		action1.setImageDescriptor(ReadmeImages.EDITOR_ACTION1_IMAGE_ENABLE);
		action1.setHoverImageDescriptor(ReadmeImages.EDITOR_ACTION1_IMAGE);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(action1, IReadmeConstants.EDITOR_ACTION1_CONTEXT);

		action2 = new RetargetAction(IReadmeConstants.RETARGET2, MessageUtil.getString("Editor_Action2")); //$NON-NLS-1$
		action2.setToolTipText(MessageUtil.getString("Readme_Editor_Action2")); //$NON-NLS-1$
		action2.setDisabledImageDescriptor(ReadmeImages.EDITOR_ACTION2_IMAGE_DISABLE);
		action2.setImageDescriptor(ReadmeImages.EDITOR_ACTION2_IMAGE_ENABLE);
		action2.setHoverImageDescriptor(ReadmeImages.EDITOR_ACTION2_IMAGE);

		action3 = new LabelRetargetAction(IReadmeConstants.LABELRETARGET3, MessageUtil.getString("Editor_Action3")); //$NON-NLS-1$
		action3.setDisabledImageDescriptor(ReadmeImages.EDITOR_ACTION3_IMAGE_DISABLE);
		action3.setImageDescriptor(ReadmeImages.EDITOR_ACTION3_IMAGE_ENABLE);
		action3.setHoverImageDescriptor(ReadmeImages.EDITOR_ACTION3_IMAGE);

		handler2 = new EditorAction(MessageUtil.getString("Editor_Action2")); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(action2, IReadmeConstants.EDITOR_ACTION2_CONTEXT);

		handler3 = new EditorAction(MessageUtil.getString("Editor_Action3")); //$NON-NLS-1$
		handler3.setToolTipText(MessageUtil.getString("Readme_Editor_Action3")); //$NON-NLS-1$
		PlatformUI.getWorkbench().getHelpSystem().setHelp(action3, IReadmeConstants.EDITOR_ACTION3_CONTEXT);

		handler4 = new EditorAction(MessageUtil.getString("Editor_Action4")); //$NON-NLS-1$
		handler5 = new EditorAction(MessageUtil.getString("Editor_Action5")); //$NON-NLS-1$
		handler5.setToolTipText(MessageUtil.getString("Readme_Editor_Action5")); //$NON-NLS-1$

		dirtyStateContribution = new DirtyStateContribution();
	}

	@Override
