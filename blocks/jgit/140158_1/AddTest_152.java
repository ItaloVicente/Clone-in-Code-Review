package org.eclipse.jgit.pgm;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.TextBuiltin.TerminatedByHelpException;
import org.eclipse.jgit.util.IO;

public class CLIGitCommand extends Main {

	private final Result result;

	private final Repository db;

	public CLIGitCommand(Repository db) {
		super();
		this.db = db;
		result = new Result();
	}

	public static void main(String[] args) throws Exception {
		String workDir = System.getProperty("git_work_tree");
		if (workDir == null) {
			workDir = ".";
			System.out.println(
					"System property 'git_work_tree' not specified
							+ new File(workDir).getAbsolutePath());
		}
		try (Repository db = new FileRepository(workDir + "/.git")) {
			for (String cmd : args) {
				List<String> result = execute(cmd
				for (String line : result) {
					System.out.println(line);
				}
			}
		}
	}

	public static List<String> execute(String str
			throws Exception {
		Result result = executeRaw(str
		return getOutput(result);
	}

	public static Result executeRaw(String str
			throws Exception {
		CLIGitCommand cmd = new CLIGitCommand(db);
		cmd.run(str);
		return cmd.result;
	}

	public static List<String> executeUnchecked(String str
			throws Exception {
		CLIGitCommand cmd = new CLIGitCommand(db);
		try {
			cmd.run(str);
			return getOutput(cmd.result);
		} catch (Throwable e) {
			return cmd.result.errLines();
		}
	}

	private static List<String> getOutput(Result result) {
		if (result.ex instanceof TerminatedByHelpException) {
			return result.errLines();
		}
		return result.outLines();
	}

	private void run(String commandLine) throws Exception {
		String[] argv = convertToMainArgs(commandLine);
		try {
			super.run(argv);
		} catch (TerminatedByHelpException e) {
		} finally {
			writer.flush();
		}
	}

	private static String[] convertToMainArgs(String str)
			throws Exception {
		String[] args = split(str);
		if (!args[0].equalsIgnoreCase("git") || args.length < 2) {
			throw new IllegalArgumentException(
					"Expected 'git <command> [<args>]'
		}
		String[] argv = new String[args.length - 1];
		System.arraycopy(args
		return argv;
	}

	@Override
	PrintWriter createErrorWriter() {
		return new PrintWriter(new OutputStreamWriter(
				result.err
	}

	@Override
	void init(TextBuiltin cmd) throws IOException {
		cmd.outs = result.out;
		cmd.errs = result.err;
		super.init(cmd);
	}

	@Override
	protected Repository openGitDir(String aGitdir) throws IOException {
		assertNull(aGitdir);
		return db;
	}

	@Override
	void exit(int status
		if (t == null) {
			t = new IllegalStateException(Integer.toString(status));
		}
		result.ex = t;
		throw t;
	}

	static String[] split(String commandLine) {
		final List<String> list = new ArrayList<>();
		boolean inquote = false;
		boolean inDblQuote = false;
		StringBuilder r = new StringBuilder();
		for (int ip = 0; ip < commandLine.length();) {
			final char b = commandLine.charAt(ip++);
			switch (b) {
			case '\t':
			case ' ':
				if (inquote || inDblQuote)
					r.append(b);
				else if (r.length() > 0) {
					list.add(r.toString());
					r = new StringBuilder();
				}
				continue;
			case '\"':
				if (inquote)
					r.append(b);
				else
					inDblQuote = !inDblQuote;
				continue;
			case '\'':
				if (inDblQuote)
					r.append(b);
				else
					inquote = !inquote;
				continue;
			case '\\':
				if (inDblQuote || inquote || ip == commandLine.length())
				else
					r.append(commandLine.charAt(ip++));
				continue;
			default:
				r.append(b);
				continue;
			}
		}
		if (r.length() > 0)
			list.add(r.toString());
		return list.toArray(new String[0]);
	}

	public static class Result {
		public final ByteArrayOutputStream out = new ByteArrayOutputStream();

		public final ByteArrayOutputStream err = new ByteArrayOutputStream();

		public Exception ex;

		public byte[] outBytes() {
			return out.toByteArray();
		}

		public byte[] errBytes() {
			return err.toByteArray();
		}

		public String outString() {
			return new String(out.toByteArray()
		}

		public List<String> outLines() {
			return IO.readLines(outString());
		}

		public String errString() {
			return new String(err.toByteArray()
		}

		public List<String> errLines() {
			return IO.readLines(errString());
		}
	}

}
