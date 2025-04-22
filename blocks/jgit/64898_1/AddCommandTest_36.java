package org.eclipse.jgit.pgm.opt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class OptionWithValuesListHandler extends OptionHandler<List<?>> {

	public OptionWithValuesListHandler(CmdLineParser parser
			OptionDef option
		super(parser
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		final List<String> list = new ArrayList<>();
		for (int idx = 0; idx < params.size(); idx++) {
			final String p;
			try {
				p = params.getParameter(idx);
			} catch (CmdLineException cle) {
				break;
			}
			list.add(p);
		}
		setter.addValue(list);
		return list.size();
	}

	@Override
	public String getDefaultMetaVariable() {
		return CLIText.get().metaVar_values;
	}

}
