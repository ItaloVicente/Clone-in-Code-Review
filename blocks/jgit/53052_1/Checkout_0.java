package org.eclipse.jgit.pgm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.CmdLineParser;
import org.eclipse.jgit.pgm.opt.SubcommandHandler;
import org.eclipse.jgit.util.IO;
import org.kohsuke.args4j.Argument;

public class CLIGitCommand {
	@Argument(index = 0
	private TextBuiltin subcommand;

	@Argument(index = 1
	private List<String> arguments = new ArrayList<String>();

	public TextBuiltin getSubcommand() {
		return subcommand;
	}

	public List<String> getArguments() {
		return arguments;
	}

	public static List<String> execute(String str
		try {
			return IO.readLines(new String(rawExecute(str
		} catch (Die e) {
			return IO.readLines(MessageFormat.format(CLIText.get().fatalError
		}
	}

	public static byte[] rawExecute(String str
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		rawExecute(str
		return baos.toByteArray();
	}

	public static Charset getOutputEncoding(Repository repository) {
		String encoding = repository != null ? repository.getConfig()
				.getString("i18n"
		return encoding != null ? Charset.forName(encoding) : Charset
				.defaultCharset();
	}

	public static void rawExecute(String str
			OutputStream outputStream
			throws Exception {
		String[] args = split(str);
			throw new IllegalArgumentException(
					"Expected 'git <command> [<args>]'
		}
		String[] argv = new String[args.length - 1];
		System.arraycopy(args

		CLIGitCommand bean = new CLIGitCommand();
		final CmdLineParser clp = new CmdLineParser(bean);
		clp.parseArgument(argv);

		final TextBuiltin cmd = bean.getSubcommand();
		cmd.outs = outputStream;
		cmd.errs = errorStream;
		cmd.init(db
		try {
			cmd.execute(bean.getArguments().toArray(new String[bean.getArguments().size()]));
		} finally {
			if (cmd.outw != null) {
				cmd.outw.flush();
			}
			if (cmd.errw != null) {
				cmd.errw.flush();
			}
		}
	}

	static String[] split(String commandLine) {
		final List<String> list = new ArrayList<String>();
		boolean inquote = false;
		boolean inDblQuote = false;
		StringBuilder r = new StringBuilder();
		for (int ip = 0; ip < commandLine.length();) {
			final char b = commandLine.charAt(ip++);
			switch (b) {
			case '\t':
			case ' ':
				if (inquote || inDblQuote) {
					r.append(b);
				} else if (r.length() > 0) {
					list.add(r.toString());
					r = new StringBuilder();
				}
				continue;
			case '\"':
				if (inquote) {
					r.append(b);
				} else {
					inDblQuote = !inDblQuote;
				}
				continue;
			case '\'':
				if (inDblQuote) {
					r.append(b);
				} else {
					inquote = !inquote;
				}
				continue;
			default:
				r.append(b);
				continue;
			}
		}
		if (r.length() > 0) {
			list.add(r.toString());
		}
		return list.toArray(new String[list.size()]);
	}
}
