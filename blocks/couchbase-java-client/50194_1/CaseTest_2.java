
package com.couchbase.client.java.query.dsl.functions;

import static com.couchbase.client.java.query.dsl.Expression.s;
import static com.couchbase.client.java.query.dsl.Expression.x;
import static org.junit.Assert.*;

import com.couchbase.client.java.query.dsl.Expression;
import org.junit.Test;

public class CaseTest {

    @Test
    public void testCaseSimple() throws Exception {
        Expression caseSimple = Case.caseSimple(x("user.id")).when(x("other.id")).then(x(1)).elseReturn(x(2));

        assertEquals("CASE user.id WHEN other.id THEN 1 ELSE 2 END", caseSimple.toString());
    }

    @Test
    public void testCaseSearch() throws Exception {
        Expression caseSearch = Case.caseSearch().when(x("other.age").lt(2)).then(s("baby")).elseReturn(s("not baby"));

        assertEquals("CASE WHEN other.age < 2 THEN \"baby\" ELSE \"not baby\" END", caseSearch.toString());
    }
}
