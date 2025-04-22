package org.eclipse.urischeme.internal.registration;

import java.io.IOException;

public interface IProcessExecutor {

	String execute(String command, String... args) throws IOException;

}
