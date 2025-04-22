
package org.eclipse.jgit.pgm.opt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.PathFilterGroup;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class PathTreeFilterHandler extends OptionHandler<TreeFilter> {
	public PathTreeFilterHandler(final CmdLineParser parser
			final OptionDef option
		super(parser
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		final List<PathFilter> filters = new ArrayList<>();
		for (int idx = 0;; idx++) {
			final String path;
			try {
				path = params.getParameter(idx);
			} catch (CmdLineException cle) {
				break;
			}
			filters.add(PathFilter.create(path));
		}

		if (filters.isEmpty())
			return 0;
		if (filters.size() == 1) {
			setter.addValue(filters.get(0));
			return 1;
		}
		setter.addValue(PathFilterGroup.create(filters));
		return filters.size();
	}

	@Override
	public String getDefaultMetaVariable() {
		return CLIText.get().metaVar_paths;
	}
}
