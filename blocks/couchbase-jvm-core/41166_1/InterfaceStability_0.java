package com.couchbase.client.core.annotations;

import java.lang.annotation.Documented;

@InterfaceStability.Experimental
@InterfaceAudience.Public
public class InterfaceAudience {

    @Documented
    public @interface Private {};

    @Documented
    public @interface Public {};

}
