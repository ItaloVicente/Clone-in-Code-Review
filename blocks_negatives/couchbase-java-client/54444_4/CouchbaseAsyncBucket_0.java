        Query query;
        if (statement instanceof PreparedPayload) {
            PreparedPayload preparedPayload = (PreparedPayload) statement;
            query = Query.prepared(preparedPayload);
        } else {
            query = Query.simple(statement);
        }
        return query(query);
