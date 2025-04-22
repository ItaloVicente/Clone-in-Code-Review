package org.eclipse.urischeme.internal.registration;

public interface IProcessExecutor {

	String execute(String command, String... args) throws Exception;

}
