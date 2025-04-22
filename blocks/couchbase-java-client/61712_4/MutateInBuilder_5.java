
package com.couchbase.client.java.subdoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;

@InterfaceStability.Experimental
@InterfaceAudience.Private
public class MultiValue<T> implements Iterable<T> {

    private final List<T> values;

    public MultiValue(T... values) {
        if (values == null || values.length < 1) {
            throw new IllegalArgumentException("MultiValue does not make sense with a null or empty array of elements");
        }
        this.values = new ArrayList<T>(values.length);
        Collections.addAll(this.values, values);
    }

    public MultiValue(Collection<T> values) {
        if (values == null || values.size() < 1) {
            throw new IllegalArgumentException("MultiValue does not make sense with a null or empty collection of elements");
        }
        this.values = new ArrayList<T>(values);
    }

    public int size() {
        return values.size();
    }

    @Override
    public Iterator<T> iterator() {
        return values.iterator();
    }
}
