        Menu refreshMenu = new Menu(getShell(), SWT.POP_UP);
        autoRefresh = new MenuItem(refreshMenu, SWT.CHECK);
        autoRefresh.setText(Messages.actionWebBrowserAutoRefresh);
        refresh.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> {
            if (e.detail == SWT.ARROW) {
                refreshMenu.setVisible(true);
            } else {
                refresh();
            }
        }));
        autoRefresh.addSelectionListener(SelectionListener.widgetSelectedAdapter(e -> autoRefresh()));

        return toolbar;
