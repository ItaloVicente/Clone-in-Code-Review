        setText(action.getText());
        setToolTipText(action.getToolTipText());
        setId(action.getId());
        setActionDefinitionId(action.getActionDefinitionId());
        window.getWorkbench().getHelpSystem().setHelp(this,
				IWorkbenchHelpContextIds.IMPORT_ACTION);
        setImageDescriptor(action.getImageDescriptor());
    }
