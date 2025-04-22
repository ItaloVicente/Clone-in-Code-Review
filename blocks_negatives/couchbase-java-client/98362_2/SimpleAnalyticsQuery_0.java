        statement = statement.trim();
        if (!statement.endsWith(";")) {
            statement = statement + ";";
        }

        this.statement = statement;
