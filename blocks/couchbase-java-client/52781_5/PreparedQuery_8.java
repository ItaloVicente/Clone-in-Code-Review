package com.couchbase.client.java.query;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceAudience.Public
@InterfaceStability.Experimental
public class PreparedPayload implements SerializableStatement {

    private static final long serialVersionUID = -5950676152745796617L;

    private final String preparedName;
    private final SerializableStatement originalStatement;

    public PreparedPayload(Statement originalStatement, String preparedName) {
        this.originalStatement = originalStatement instanceof SerializableStatement
                ? (SerializableStatement) originalStatement
                : new Query.RawStatement(originalStatement.toString());
        this.preparedName = preparedName;
    }

    public String payload() {
        return preparedName;
    }

    public String preparedName() {
        return preparedName;
    }

    public SerializableStatement originalStatement() {
        return originalStatement;
    }

    @Override
    public String toString() {
        return payload();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PreparedPayload that = (PreparedPayload) o;

        if (!preparedName.equals(that.preparedName)) {
            return false;
        }
        return originalStatement.equals(that.originalStatement);

    }

    @Override
    public int hashCode() {
        int result = preparedName.hashCode();
        result = 31 * result + originalStatement.hashCode();
        return result;
    }
}
