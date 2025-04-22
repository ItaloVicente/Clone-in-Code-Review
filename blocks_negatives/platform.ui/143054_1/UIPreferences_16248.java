                .iterator();
        if (!pages.hasNext()) {
            return null;
        }
            title = NLS.bind(WorkbenchMessages.PropertyDialog_propertyMessage, (new Object[] { name }));
            dialog = new PropertyDialogWrapper(getShell(), manager,
                    new StructuredSelection(element));
            dialog.create();
            dialog.getShell().setText(title);
            PlatformUI.getWorkbench().getHelpSystem().setHelp(dialog.getShell(),
                    IWorkbenchHelpContextIds.PROPERTY_DIALOG);
            for (Object element2 : manager.getElements(
                    PreferenceManager.PRE_ORDER)) {
            IPreferenceNode node = (IPreferenceNode) element2;
            if (node.getId().equals(id)) {
