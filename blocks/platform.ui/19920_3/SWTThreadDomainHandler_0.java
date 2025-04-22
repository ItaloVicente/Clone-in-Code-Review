package org.eclipse.e4.ui.di;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Target({ METHOD })
@Retention(RUNTIME)
@Documented
public @interface UIInject {

}
