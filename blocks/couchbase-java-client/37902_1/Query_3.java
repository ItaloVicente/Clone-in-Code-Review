    public Query where(Expression expression) {
        this.where = expression;
        return this;
    }

    public Query from(final String from) {
        this.from = from;
        return this;
    }

    public Query groupBy(final Expression groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public Query having(final Expression having) {
        this.having = having;
        return this;
    }

    public Query orderBy(final Sort... sorts) {
        this.sorts = sorts;
        return this;
    }

    public Query limit(final int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("Limit must be larger or equal to zero.");
        }
        this.limit = limit;
        return this;
    }

    public Query offset(final int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must be larger or equal to zero.");
        }
        this.offset = offset;
        return this;
    }

    public boolean hasFrom() {
        return from != null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (selects != null && selects.length > 0) {
            builder.append("SELECT ");
            for (int i = 0; i < selects.length; i++) {
                builder.append(selects[i]);
                if (i < selects.length-1) {
                    builder.append(",");
                }
                builder.append(" ");
            }
        }

        if (from != null) {
            builder.append("FROM " + from).append(" ");
        }

        if (where != null) {
            builder.append("WHERE " + where.toString()).append(" ");
        }

        if (groupBy != null) {
            builder.append("GROUP BY ").append(groupBy.toString()).append(" ");
            if (having != null) {
                builder.append("HAVING ").append(having.toString()).append(" ");
            }
        }
        if (sorts != null) {
            builder.append("ORDER BY ");
            for (int i = 0; i < sorts.length; i++) {
                builder.append(sorts[i].toString());
                if (i < sorts.length) {
                    builder.append(", ");
                }
            }
        }
        if (limit >= 0) {
            builder.append("LIMIT ").append(limit).append(" ");
        }
        if (offset >= 0) {
            builder.append("OFFSET ").append(offset);
        }

        return builder.toString().trim();
