    }

    void createColumns() {
        SelectionListener headerListener = new SelectionAdapter() {
            /**
             * Handles the case of user selecting the
             * header area.
             * <p>If the column has not been selected previously,
             * it will set the sorter of that column to be
             * the current tasklist sorter. Repeated
             * presses on the same column header will
             * toggle sorting order (ascending/descending).
             */
            @Override
