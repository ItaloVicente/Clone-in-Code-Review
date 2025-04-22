
package org.eclipse.ui.tests.internal;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ForcedException extends RuntimeException {

	 private static final long serialVersionUID= 1L;

	 public ForcedException(String message) {
		 super(message);
	 }
	 
	 @Override
	public void printStackTrace(PrintStream s) {
	 		 s.println("!FORCED BY TEST: this entry is intentional: " + getMessage());
	 }
	 		 		 
	 @Override
	public void printStackTrace(PrintWriter s) {
	 		 s.println("!FORCED BY TEST: this entry is intentional:" + getMessage());
	 }
}

