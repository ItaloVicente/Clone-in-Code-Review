
package com.couchbase.client.java.repository.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.couchbase.client.core.annotations.InterfaceStability;
import com.couchbase.client.core.encryption.InsecureKeyStoreProvider;
import com.couchbase.client.core.encryption.KeyStoreProvider;

@InterfaceStability.Uncommitted
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface EncryptedField {
	String provider() default "AES-256";
}
