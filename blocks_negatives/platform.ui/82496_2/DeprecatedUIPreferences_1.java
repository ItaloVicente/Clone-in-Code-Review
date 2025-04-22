		WorkbenchHelp.setHelp(dialog.getShell(),
				new Object[] { IWorkbenchHelpContextIds.PROPERTY_DIALOG });
		for (Object element2 : manager.getElements(
				PreferenceManager.PRE_ORDER)) {
IPreferenceNode node = (IPreferenceNode) element2;
if (node.getId().equals(id)) {
		dialog.showPage(node);
		break;
}
