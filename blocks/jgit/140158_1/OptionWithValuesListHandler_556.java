
package org.eclipse.jgit.pgm.opt;

import java.io.IOException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class ObjectIdHandler extends OptionHandler<ObjectId> {
	private final org.eclipse.jgit.pgm.opt.CmdLineParser clp;

	public ObjectIdHandler(final CmdLineParser parser
			final Setter<? super ObjectId> setter) {
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
		if (id != null) {
			setter.addValue(id);
			return 1;
		}

		throw new CmdLineException(clp
				CLIText.format(CLIText.get().notAnObject)
	}

	@Override
	public String getDefaultMetaVariable() {
		return CLIText.get().metaVar_object;
	}
}
