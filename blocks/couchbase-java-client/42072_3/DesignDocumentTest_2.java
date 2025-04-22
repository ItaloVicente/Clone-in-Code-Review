package com.couchbase.client.java.view;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultViewTest {

    @Test
    public void shouldEqualBasedOnItsProperties() {
        View view1 = DefaultView.create("name", "map", "reduce");
        View view2 = DefaultView.create("name", "map", "reduce");
        assertTrue(view1.equals(view2));

        view1 = DefaultView.create("name", "map", "reduce");
        view2 = DefaultView.create("foobar", "map", "reduce");
        assertFalse(view1.equals(view2));

        view1 = DefaultView.create("name", "map", "reduce");
        view2 = DefaultView.create("name", "map");
        assertFalse(view1.equals(view2));

        view1 = DefaultView.create("name", "map", "reduce");
        view2 = DefaultView.create("name", "map", "foobar");
        assertFalse(view1.equals(view2));

        view1 = DefaultView.create("name", "map", "reduce");
        view2 = DefaultView.create("name", "foobar", "reduce");
        assertFalse(view1.equals(view2));
    }

}
