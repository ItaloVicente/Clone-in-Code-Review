
package org.eclipse.jgit.diffmergetool;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;

public class CommandExecutor {

	public CommandExecutor() {
	}

	static public ExecutionResult run(FS fs
			Map<String
			throws IOException
		String[] args;
		if (splitCommands) {
			String[] cmdarray = splitSpacesAndQuotes(command
			command = cmdarray[0];
			args = Arrays.copyOfRange(cmdarray
		} else {
			args = new String[0];
		}
		ProcessBuilder pb = fs.runInShell(command
		pb.directory(workingDir);
		Map<String
		if (env != null) {
			envp.putAll(env);
		}
		ExecutionResult result = fs.execute(pb
		return result;
	}

	static public String[] splitSpacesAndQuotes(String str
			boolean skipOuterQuotes) {
		ArrayList<String> strings = new ArrayList<>();
		boolean inQuote = false;
		char quote = '\"';
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c == '\"' || c == '\'' || c == ' ' && !inQuote) {
				if (c == '\"' || c == '\'') {
					if (!inQuote) {
						quote = c;
						inQuote = true;
					} else {
						if (quote == c) {
							inQuote = false;
						}
					}
					if (skipOuterQuotes || (inQuote && (quote != c))) {
						sb.append(c);
					}
				}
				if (!inQuote && sb.length() > 0) {
					strings.add(sb.toString());
					sb.delete(0
				}
			} else {
				sb.append(c);
			}
		}
		return strings.toArray(new String[strings.size()]);
	}

}
