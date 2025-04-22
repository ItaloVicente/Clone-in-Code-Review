
package org.eclipse.jgit.pgm.opt;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevFlag;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class RevCommitHandler extends OptionHandler<RevCommit> {
	private final org.eclipse.jgit.pgm.opt.CmdLineParser clp;

	public RevCommitHandler(final CmdLineParser parser
			final Setter<? super RevCommit> setter) {
		super(parser
		clp = (org.eclipse.jgit.pgm.opt.CmdLineParser) parser;
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		String name = params.getParameter(0);

		boolean interesting = true;
			name = name.substring(1);
			interesting = false;
		}

		if (dot2 != -1) {
			if (!option.isMultiValued())
				throw new CmdLineException(clp
						CLIText.format(CLIText.get().onlyOneMetaVarExpectedIn)
						option.metaVar()

			final String left = name.substring(0
			final String right = name.substring(dot2 + 2);
			addOne(left
			addOne(right
			return 1;
		}

		addOne(name
		return 1;
	}

	private void addOne(String name
			throws CmdLineException {
		final ObjectId id;
		try {
			id = clp.getRepository().resolve(name);
		} catch (IOException e) {
			throw new CmdLineException(clp
		}
		if (id == null)
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().notACommit)

		final RevCommit c;
		try {
			c = clp.getRevWalk().parseCommit(id);
		} catch (MissingObjectException | IncorrectObjectTypeException e) {
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().notACommit)
		} catch (IOException e) {
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().cannotReadBecause)
					e.getMessage());
		}

		if (interesting)
			c.remove(RevFlag.UNINTERESTING);
		else
			c.add(RevFlag.UNINTERESTING);

		setter.addValue(c);
	}

	@Override
	public String getDefaultMetaVariable() {
		return CLIText.get().metaVar_commitish;
	}
}
