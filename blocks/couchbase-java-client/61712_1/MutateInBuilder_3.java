
package com.couchbase.client.java.subdoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MultiValue<T> implements Iterable<T> {

    private final List<T> values;

    public MultiValue(T... values) {
        if (values == null || values.length < 2) {
            throw new IllegalArgumentException("MultiValue does not make sense with a null or smaller than 2 array of elements");
        }
        this.values = new ArrayList<T>(values.length);
        Collections.addAll(this.values, values);
    }

    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }
}
