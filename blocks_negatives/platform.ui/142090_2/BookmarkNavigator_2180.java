                comparator.saveState(settings);
            }
        };

        TableLayout layout = new TableLayout();
        table.setLayout(layout);
        table.setHeaderVisible(true);
        for (int i = 0; i < columnHeaders.length; i++) {
            layout.addColumnData(columnLayouts[i]);
            TableColumn tc = new TableColumn(table, SWT.NONE, i);
            tc.setResizable(columnLayouts[i].resizable);
            tc.setText(columnHeaders[i]);
            if (i > 0) {
