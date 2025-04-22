package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.LocalDiskRepositoryTestCase;
import org.eclipse.jgit.pgm.CLIGitCommand;
import org.eclipse.jgit.pgm.CLIGitCommand.Result;
import org.eclipse.jgit.pgm.TextBuiltin.TerminatedByHelpException;
import org.junit.Before;

public class CLIRepositoryTestCase extends LocalDiskRepositoryTestCase {
	protected Repository db;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		db = createWorkRepository();
	}

	protected String[] executeUnchecked(String... cmds) throws Exception {
		List<String> result = new ArrayList<>(cmds.length);
		for (String cmd : cmds) {
			result.addAll(CLIGitCommand.executeUnchecked(cmd
		}
		return result.toArray(new String[0]);
	}

	protected String[] execute(String... cmds) throws Exception {
		List<String> result = new ArrayList<>(cmds.length);
		for (String cmd : cmds) {
			Result r = CLIGitCommand.executeRaw(cmd
			if (r.ex instanceof TerminatedByHelpException) {
				result.addAll(r.errLines());
			} else if (r.ex != null) {
				throw r.ex;
			}
			result.addAll(r.outLines());
		}
		return result.toArray(new String[0]);
	}

	protected Path writeLink(String link
		return JGitTestUtil.writeLink(db
	}

	protected File writeTrashFile(String name
			throws IOException {
		return JGitTestUtil.writeTrashFile(db
	}

	@Override
	protected String read(File file) throws IOException {
		return JGitTestUtil.read(file);
	}

	protected void deleteTrashFile(String name) throws IOException {
		JGitTestUtil.deleteTrashFile(db
	}

	protected String[] executeAndPrint(String... cmds) throws Exception {
		String[] lines = execute(cmds);
		for (String line : lines) {
			System.out.println(line);
		}
		return lines;
	}

	protected String[] executeAndPrintTestCode(String... cmds) throws Exception {
		String[] lines = execute(cmds);
		String cmdString = cmdString(cmds);
		if (lines.length == 0)
			System.out.println("\t\tassertTrue(execute(" + cmdString
					+ ").length == 0);");
		else {
			System.out
			System.out.print("\t\t\t\t\t\t\"" + escapeJava(lines[0]));
			for (int i=1; i<lines.length; i++) {
				System.out.println("\"
				System.out.print("\t\t\t\t\t\t\"" + escapeJava(lines[i]));
			}
			System.out.println("\t\t\t\t}
		}
		return lines;
	}

	protected String cmdString(String... cmds) {
		if (cmds.length == 0)
			return "";
		else if (cmds.length == 1)
			return "\"" + escapeJava(cmds[0]) + "\"";
		else {
			StringBuilder sb = new StringBuilder(cmdString(cmds[0]));
			for (int i=1; i<cmds.length; i++) {
				sb.append("
				sb.append(cmdString(cmds[i]));
			}
			return sb.toString();
		}
	}

	protected String escapeJava(String line) {
		return line.replaceAll("\""
				.replaceAll("\\\\"
				.replaceAll("\t"
	}

	protected void assertStringArrayEquals(String expected
		assertEquals(1
				actual.length > 1 && actual[actual.length - 1].equals("")
						? actual.length - 1 : actual.length);
		assertEquals(expected
	}

	protected void assertArrayOfLinesEquals(String[] expected
		assertEquals(toString(expected)
	}

	public static String toString(String... lines) {
		return toString(Arrays.asList(lines));
	}

	public static String toString(List<String> lines) {
		StringBuilder b = new StringBuilder();
		for (String s : lines) {
			s = s.trim();
			if (s != null && !s.isEmpty()) {
				b.append(s);
				b.append('\n');
			}
		}
		if (b.length() > 0 && b.charAt(b.length() - 1) == '\n') {
			b.deleteCharAt(b.length() - 1);
		}
		return b.toString();
	}

	public static boolean contains(List<String> lines
		for (String s : lines) {
			if (s.contains(str)) {
				return true;
			}
		}
		return false;
	}
}
