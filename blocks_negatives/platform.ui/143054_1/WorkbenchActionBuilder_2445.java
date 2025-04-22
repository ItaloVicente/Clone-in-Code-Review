            helpToolBar.add(new GroupMarker(IWorkbenchActionConstants.GROUP_APP));
            coolBar.add(actionBarConfigurer.createToolBarContributionItem(helpToolBar,
                    IWorkbenchActionConstants.TOOLBAR_HELP));
        }

    }

    /**
     * Fills the menu bar with the workbench actions.
     */
    @Override
