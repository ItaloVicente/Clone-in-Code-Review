package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import java.util.ArrayList;
import java.util.List;

import com.couchbase.client.java.query.dsl.Expression;

public class Case {

    public static CaseClause caseSimple(Expression caseExpression, Expression when, Expression then) {
        return new CaseBuilder(caseExpression, when, then);
    }

    public static CaseClause caseSearch(Expression whenCondition, Expression then) {
        return new CaseBuilder(null, whenCondition, then);
    }


    public interface CaseClause extends WhenClause {
        Expression end();

        Expression elseEnd(Expression elseResult);
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

        private CaseBuilder(Expression caseExpression, Expression when, Expression then) {
            this.caseExpression = caseExpression;
            this.whens = new ArrayList<Expression>(1);
            this.thens = new ArrayList<Expression>(1);

            whens.add(when);
            thens.add(then);
            this.count++;
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
        public Expression elseEnd(Expression elseResult) {
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
