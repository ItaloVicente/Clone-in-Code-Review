            for (int i = 0; i < cache.size(); i++) {
                ((EditorContribution) cache.get(i)).contribute(bars
                        .getMenuManager(), false, bars.getToolBarManager(),
                        true);
            }
        }
