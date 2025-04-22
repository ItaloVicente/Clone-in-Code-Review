package org.eclipse.jgit.pgm;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.lib.Repository;
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
			throws Exception {
		String[] args = split(str);
		if (!args[0].equalsIgnoreCase("git") || args.length < 2)
			throw new IllegalArgumentException(
					"Expected 'git <command> [<args>]'
		String[] argv = new String[args.length - 1];
		System.arraycopy(args

		CLIGitCommand bean = new CLIGitCommand();
		final CmdLineParser clp = new CmdLineParser(bean);
		clp.parseArgument(argv);

		final TextBuiltin cmd = bean.getSubcommand();
		if (cmd.requiresRepository())
			cmd.init(db
		else
			cmd.init(null
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		cmd.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
				baos)));
		try {
			cmd.execute(bean.getArguments().toArray(
					new String[bean.getArguments().size()]));
		} catch (Die e) {
			return IO.readLines(e.getMessage());
		} finally {
			if (cmd.out != null)
				cmd.out.flush();
		}
		return IO.readLines(baos.toString());
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
				if (inquote || ip == commandLine.length())
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
		return list.toArray(new String[list.size()]);
	}

}
