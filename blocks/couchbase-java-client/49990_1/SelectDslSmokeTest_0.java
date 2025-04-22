package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.x;

import java.util.ArrayList;
import java.util.List;

import com.couchbase.client.java.query.dsl.Expression;

public class Collections {

    private abstract static class CollectionBuilder {
        private Expression prefix;
        private List<Expression> variables;

        private CollectionBuilder(Expression prefix, String firstVar, Expression firstExpr, boolean useIn) {
            this.prefix = prefix;
            this.variables = new ArrayList<Expression>(2);
            if (useIn) {
                in(firstVar, firstExpr);
            } else {
                within(firstVar, firstExpr);
            }
        }

        protected CollectionBuilder in(String variable, Expression expression) {
            variables.add(x(variable + " IN " + expression.toString()));
            return this;
        }

        protected CollectionBuilder within(String variable, Expression expression) {
            variables.add(x(variable + " WITHIN " + expression.toString()));
            return this;
        }

        public final Expression end() {
            return end(null, null);
        }

        protected final Expression end(String conditionKeyword, Expression condition) {
            StringBuilder sb = new StringBuilder(prefix.toString()).append(' ');
            for (Expression variable : variables) {
                sb.append(variable).append(", ");
            }
            if (!variables.isEmpty()) {
                sb.delete(sb.length() - 2, sb.length());
            }
            if (condition != null && conditionKeyword != null) {
                sb.append(' ').append(conditionKeyword.trim()).append(' ');
                sb.append(condition.toString());
            }
            sb.append(" END");
            return x(sb.toString());
        }

    }

    public static final class SatisfiesBuilder extends CollectionBuilder {

        private SatisfiesBuilder(Expression prefix, String firstVar, Expression firstExpr, boolean useIn) {
            super(prefix, firstVar, firstExpr, useIn);
        }

        @Override
        public SatisfiesBuilder in(String variable, Expression expression) {
            return (SatisfiesBuilder) super.in(variable, expression);
        }

        @Override
        public SatisfiesBuilder within(String variable, Expression expression) {
            return (SatisfiesBuilder) super.within(variable, expression);
        }

        public Expression satisfies(Expression condition) {
            return super.end("SATISFIES", condition);
        }
    }

    public static final class WhenBuilder extends CollectionBuilder {

        private WhenBuilder(Expression prefix, String firstVar, Expression firstExpr, boolean useIn) {
            super(prefix, firstVar, firstExpr, useIn);
        }

        @Override
        public WhenBuilder in(String variable, Expression expression) {
            return (WhenBuilder) super.in(variable, expression);
        }

        @Override
        public WhenBuilder within(String variable, Expression expression) {
            return (WhenBuilder) super.within(variable, expression);
        }

        public Expression when(Expression condition) {
            return super.end("WHEN", condition);
        }
    }

    public static SatisfiesBuilder anyIn(String variable, Expression expression) {
        return new SatisfiesBuilder(x("ANY"), variable, expression, true);
    }

    public static SatisfiesBuilder anyWithin(String variable, Expression expression) {
        return new SatisfiesBuilder(x("ANY"), variable, expression, false);
    }


    public static SatisfiesBuilder everyIn(String variable, Expression expression) {
        return new SatisfiesBuilder(x("EVERY"), variable, expression, true);
    }


    public static SatisfiesBuilder everyWithin(String variable, Expression expression) {
        return new SatisfiesBuilder(x("EVERY"), variable, expression, false);
    }

    public static WhenBuilder arrayIn(Expression arrayExpression, String variable, Expression expression) {
        return new WhenBuilder(x("ARRAY " + arrayExpression.toString() + " FOR"),
                variable, expression, true);
    }

    public static WhenBuilder arrayWithin(Expression arrayExpression, String variable, Expression expression) {
        return new WhenBuilder(x("ARRAY " + arrayExpression.toString() + " FOR"),
                variable, expression, false);
    }

    public static WhenBuilder firstIn(Expression arrayExpression, String variable, Expression expression) {
        return new WhenBuilder(x("FIRST " + arrayExpression.toString() + " FOR"),
                variable, expression, true);
    }

    public static WhenBuilder firstWithin(Expression arrayExpression, String variable, Expression expression) {
        return new WhenBuilder(x("FIRST " + arrayExpression.toString() + " FOR"),
                variable, expression, false);
    }

}
