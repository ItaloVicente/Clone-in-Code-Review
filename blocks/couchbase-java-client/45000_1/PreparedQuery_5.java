package com.couchbase.client.java.query;

public class PrepareStatement implements Statement {

    private final Statement toPrepare;

    private PrepareStatement(Statement toPrepare) {
        this.toPrepare = toPrepare;
    }

    @Override
    public String toString() {
        return "PREPARE " + toPrepare.toString();
    }

    public static PrepareStatement prepare(Statement statement) {
        if (statement instanceof QueryPlan) {
            throw new IllegalArgumentException("QueryPlan cannot be prepared again");
        } else if (statement instanceof PrepareStatement) {
            return (PrepareStatement) statement;
        } else {
            return new PrepareStatement(statement);
        }
    }
}
