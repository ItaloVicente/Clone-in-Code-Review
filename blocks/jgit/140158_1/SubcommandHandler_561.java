
package org.eclipse.jgit.pgm.opt;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.revwalk.RevTree;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class RevTreeHandler extends OptionHandler<RevTree> {
	private final org.eclipse.jgit.pgm.opt.CmdLineParser clp;

	public RevTreeHandler(final CmdLineParser parser
			final Setter<? super RevTree> setter) {
		super(parser
		clp = (org.eclipse.jgit.pgm.opt.CmdLineParser) parser;
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		final String name = params.getParameter(0);
		final ObjectId id;
		try {
			id = clp.getRepository().resolve(name);
		} catch (IOException e) {
			throw new CmdLineException(clp
		}
		if (id == null)
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().notATree)

		final RevTree c;
		try {
			c = clp.getRevWalk().parseTree(id);
		} catch (MissingObjectException | IncorrectObjectTypeException e) {
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().notATree)
		} catch (IOException e) {
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().cannotReadBecause)
					e.getMessage());
		}
		setter.addValue(c);
		return 1;
	}

	@Override
	public String getDefaultMetaVariable() {
		return CLIText.get().metaVar_treeish;
	}
}
