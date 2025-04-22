
package org.eclipse.jgit.pgm;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target( { TYPE })
public @interface Command {
	public String name() default "";

	public String usage() default "";

	public boolean common() default false;
}
