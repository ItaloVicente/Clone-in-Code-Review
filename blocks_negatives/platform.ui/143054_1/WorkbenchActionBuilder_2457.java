                menu.add(ContributionItemFactory.VIEWS_SHOW_IN
                        .create(window));
            }
        };
        register(showInQuickMenu);

        newQuickMenu = new QuickMenuAction(newQuickMenuId) {
            @Override
