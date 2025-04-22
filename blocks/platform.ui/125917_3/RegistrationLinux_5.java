package org.eclipse.urischeme.internal.registration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProcessExecutor implements IProcessExecutor {

	@Override
	public void execute(String command, String... args) throws IOException {
		List<String> commands = new ArrayList<>();
		commands.add(command);
		commands.addAll(Arrays.asList(args));
		new ProcessBuilder(commands).start();
	}

}
