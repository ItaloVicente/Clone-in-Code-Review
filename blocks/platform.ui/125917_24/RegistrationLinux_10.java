package org.eclipse.urischeme.internal.registration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		StringBuilder builder = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			builder.append(line).append("\n"); //$NON-NLS-1$
		}
		return process.waitFor() == 0 ? builder.toString() : ""; //$NON-NLS-1$
	}

}
