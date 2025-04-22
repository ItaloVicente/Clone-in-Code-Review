            for (Object element : manager.getElements(
                    PreferenceManager.PRE_ORDER)) {
            IPreferenceNode node = (IPreferenceNode) element;
            if (node
			    .getId()
			    .equals(
			            "org.eclipse.ui.tests.dialogs.EnableTestPreferencePage")) {
			dialog.showPage(node);
			EnableTestPreferencePage page = (EnableTestPreferencePage) dialog
			        .getPage(node);
			page.flipState();
			page.flipState();
			break;
