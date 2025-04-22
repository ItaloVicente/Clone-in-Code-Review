package org.eclipse.urischeme.internal.registration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessExecutor implements IProcessExecutor {

	@Override
	public String execute(String command, String... args) throws Exception {
		List<String> commands = new ArrayList<>();
		commands.add(command);
		commands.addAll(Arrays.asList(args));
		Process process = new ProcessBuilder(commands).start();
		return process.waitFor() == 0 ? process.getOutputStream().toString() : "";//$NON-NLS-1$
	}

}
