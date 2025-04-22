package com.couchbase.client.java.document.subdoc;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.message.kv.subdoc.multi.Mutation;
import com.couchbase.client.core.message.kv.subdoc.multi.MutationCommand;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.PersistTo;
import com.couchbase.client.java.ReplicateTo;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class MutationSpec<T> {
    private final Mutation type;
    private final String path;
    private final T fragment;
    private final boolean createParents;

    MutationSpec(Mutation type, String path, T fragment, boolean createParents) {
        this.type = type;
        this.path = path;
        this.fragment = fragment;
        this.createParents = createParents;
    }

    public Mutation type() {
        return type;
    }

    public String path() {
        return path;
    }

    public T fragment() {
        return fragment;
    }

    public boolean createParents() {return createParents;}

    public static <T> MutationSpec replace(String path, T fragment) {
        return new MutationSpec<T>(Mutation.REPLACE, path, fragment, false);
    }

    public static <T> MutationSpec upsert(String path, T fragment, boolean createParents) {
        return new MutationSpec<T>(Mutation.DICT_UPSERT, path, fragment, createParents);
    }

    public static <T> MutationSpec insert(String path, T fragment, boolean createParents) {
        return new MutationSpec<T>(Mutation.DICT_ADD, path, fragment, createParents);
    }

    public static <T> MutationSpec extend(String path, T value, ExtendDirection direction, boolean createParents) {
        if (direction == ExtendDirection.FRONT) {
            return new MutationSpec<T>(Mutation.ARRAY_PUSH_FIRST, path, value, createParents);
        }
        return new MutationSpec<T>(Mutation.ARRAY_PUSH_LAST, path, value, createParents);
    }

    public static <T> MutationSpec arrayInsert(String path, T value) {
        return new MutationSpec<T>(Mutation.ARRAY_INSERT, path, value, false);
    }

    public static <T> MutationSpec addUnique(String path, T value) {
        return new MutationSpec<T>(Mutation.ARRAY_ADD_UNIQUE, path, value, false);
    }

    public static MutationSpec<Long> counter(String path, long delta) {
        return new MutationSpec<Long>(Mutation.COUNTER, path, delta, false);
    }

    public static <T> MutationSpec remove(String path) {
        return new MutationSpec<T>(Mutation.DELETE, path, null, false);
    }
}
