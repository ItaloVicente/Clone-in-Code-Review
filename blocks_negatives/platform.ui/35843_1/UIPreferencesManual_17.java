            for (Iterator iterator = manager.getElements(
                    PreferenceManager.PRE_ORDER).iterator(); iterator.hasNext();) {
                IPreferenceNode node = (IPreferenceNode) iterator.next();
                if (node
                        .getId()
                        .equals(
                                "org.eclipse.ui.tests.manual.BrokenUpdatePreferencePage")) {
                    dialog.showPage(node);
                    BrokenUpdatePreferencePage page = (BrokenUpdatePreferencePage) dialog
                            .getPage(node);
                    page.changeFont();
                    page.changePluginPreference();
                    break;
                }
