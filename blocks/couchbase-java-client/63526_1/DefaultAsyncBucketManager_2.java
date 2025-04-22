        UsingWithPath usingWithPath;
        if (whereClause != null) {
            usingWithPath = Index.createIndex(indexName).on(bucket, firstExpression, otherExpressions).where(whereClause);
        } else {
            usingWithPath = Index.createIndex(indexName).on(bucket, firstExpression, otherExpressions);
        }

