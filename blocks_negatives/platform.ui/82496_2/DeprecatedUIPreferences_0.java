			WorkbenchHelp
					.setHelp(
							dialog.getShell(),
							new Object[] { IWorkbenchHelpContextIds.PREFERENCE_DIALOG });

			for (Object element : manager.getElements(
					PreferenceManager.PRE_ORDER)) {
IPreferenceNode node = (IPreferenceNode) element;
if (node.getId().equals(id)) {
			dialog.showPage(node);
			break;
}
}
