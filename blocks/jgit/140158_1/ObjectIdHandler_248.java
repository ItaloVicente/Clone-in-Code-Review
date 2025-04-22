package org.eclipse.jgit.pgm.opt;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;
import org.kohsuke.args4j.spi.StringOptionHandler;

public class GpgSignHandler extends StringOptionHandler {


	public GpgSignHandler(CmdLineParser parser
			Setter<? super String> setter) {
		super(parser
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		String alias = params.getParameter(-1);
			try {
				String key = params.getParameter(0);
					setter.addValue(DEFAULT);
					return 0;
				}

				setter.addValue(key);
				return 1;
			} catch (CmdLineException e) {
				setter.addValue(DEFAULT);
				return 0;
			}
		}
		return 0;
	}

}
