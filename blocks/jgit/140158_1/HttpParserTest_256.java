package org.eclipse.jgit.pgm.opt;

import org.eclipse.jgit.pgm.internal.CLIText;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;
import org.kohsuke.args4j.spi.StringOptionHandler;

public class UntrackedFilesHandler extends StringOptionHandler {

	public UntrackedFilesHandler(CmdLineParser parser
			Setter<? super String> setter) {
		super(parser
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		String alias = params.getParameter(-1);
			return 0;
			return 0;
			return 0;
		} else if (params.size() == 0) {
			return 0;
		} else if (params.size() == 1) {
			String mode = params.getParameter(0);
				setter.addValue(mode);
			} else {
				throw new CmdLineException(owner
						CLIText.format(CLIText.get().invalidUntrackedFilesMode)
						mode);
			}
			return 1;
		} else {
			return super.parseArguments(params);
		}
	}

}
