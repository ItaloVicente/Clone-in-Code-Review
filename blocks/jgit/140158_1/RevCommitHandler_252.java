
package org.eclipse.jgit.pgm.opt;

import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.transport.RefSpec;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class RefSpecHandler extends OptionHandler<RefSpec> {
	public RefSpecHandler(final CmdLineParser parser
			final Setter<? super RefSpec> setter) {
		super(parser
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		setter.addValue(new RefSpec(params.getParameter(0)));
		return 1;
	}

	@Override
	public String getDefaultMetaVariable() {
		return CLIText.get().metaVar_refspec;
	}
}
