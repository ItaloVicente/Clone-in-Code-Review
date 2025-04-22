package org.eclipse.jgit.pgm.opt;

import java.time.Instant;

import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class InstantHandler extends OptionHandler<Instant> {
	public InstantHandler(CmdLineParser parser
			Setter<? super Instant> setter) {
		super(parser
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		Instant instant = Instant.parse(params.getParameter(0));
		setter.addValue(instant);
		return 1;
	}

	@Override
	public String getDefaultMetaVariable() {
		return CLIText.get().metaVar_instant;
	}
}
