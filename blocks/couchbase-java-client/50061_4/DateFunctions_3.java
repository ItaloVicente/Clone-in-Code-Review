package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import java.util.ArrayList;
import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.Expression;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class Case {

    public static WhenClause caseSimple(Expression expected) {
        return new CaseBuilder(expected);
    }

    public static WhenClause caseSearch() {
        return new CaseBuilder(null);
    }


    public interface CaseClause extends WhenClause {
        Expression end();

        Expression elseReturn(Expression elseResult);
    }

    public interface WhenClause {
        ThenClause when(Expression conditionOrExpression);
    }

    public interface ThenClause {
        CaseClause then(Expression expression);
    }


    private static final class CaseBuilder implements CaseClause, ThenClause {

        private int count = 0;
        private final List<Expression> whens;
        private final List<Expression> thens;
        private Expression elseResult = null;
        private final Expression caseExpression;

        private CaseBuilder(Expression caseExpression) {
            this.caseExpression = caseExpression;
            this.whens = new ArrayList<Expression>(1);
            this.thens = new ArrayList<Expression>(1);
        }

        @Override
        public ThenClause when(Expression conditionOrExpression) {
            whens.add(conditionOrExpression);
            thens.add(Expression.NULL());
            return this;
        }

        @Override
        public CaseClause then(Expression expression) {
            thens.set(count, expression);
            count++;
            return this;
        }

        @Override
        public Expression elseReturn(Expression elseResult) {
            this.elseResult = elseResult;
            return end();
        }

        @Override
        public Expression end() {
            StringBuilder result = new StringBuilder("CASE ");
            if (caseExpression != null) {
                result.append(caseExpression.toString()).append(' ');
            }
            for (int i = 0; i < count; i++) {
                result.append("WHEN ")
                    .append(whens.get(i))
                    .append(" THEN ")
                    .append(thens.get(i))
                    .append(", ");
            }
            result.delete(result.length() - 2, result.length());
            if (elseResult != null) {
                result.append(" ELSE ")
                        .append(elseResult.toString());
            }
            result.append(" END");
            return x(result.toString());
        }
    }
}
