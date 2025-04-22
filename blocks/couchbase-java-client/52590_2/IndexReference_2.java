package com.couchbase.client.java.query.dsl.path;

import com.couchbase.client.core.annotations.InterfaceAudience;
import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.java.query.dsl.path.index.IndexReference;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public interface HintPath extends KeysPath {

    KeysPath useIndex(IndexReference... indexes);

    KeysPath useIndex(String... indexes);
}
