            for (Iterator iterator = manager.getElements(
                    PreferenceManager.PRE_ORDER).iterator(); iterator.hasNext();) {
                IPreferenceNode node = (IPreferenceNode) iterator.next();
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
                }
