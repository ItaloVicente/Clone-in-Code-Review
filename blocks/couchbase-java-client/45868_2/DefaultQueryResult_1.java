        this.finalSuccess = finalSuccess != null && finalSuccess;
        this.allRows = new ArrayList<QueryRow>(rows.size());
        for (AsyncQueryRow row : rows) {
            this.allRows.add(new DefaultQueryRow(row.value()));
        }
        this.errors = errors;
        this.info = info;
