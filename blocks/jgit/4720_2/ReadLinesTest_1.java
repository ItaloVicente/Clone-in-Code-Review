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

	public static String[] execute(String str
		String[] args = str.split(" ");
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
		} finally {
			if (cmd.out != null)
				cmd.out.flush();
		}
		return IO.readLines(baos.toString());
	}
}
