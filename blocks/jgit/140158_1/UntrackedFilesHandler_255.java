
package org.eclipse.jgit.pgm.opt;

import org.eclipse.jgit.pgm.CommandCatalog;
import org.eclipse.jgit.pgm.CommandRef;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class SubcommandHandler extends OptionHandler<TextBuiltin> {
	private final org.eclipse.jgit.pgm.opt.CmdLineParser clp;

	public SubcommandHandler(final CmdLineParser parser
			final OptionDef option
		super(parser
		clp = (org.eclipse.jgit.pgm.opt.CmdLineParser) parser;
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		final String name = params.getParameter(0);
		final CommandRef cr = CommandCatalog.get(name);
		if (cr == null)
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().notAJgitCommand)

		owner.stopOptionParsing();
		setter.addValue(cr.create());
		return 1;
	}

	@Override
	public String getDefaultMetaVariable() {
		return CLIText.get().metaVar_command;
	}
}
