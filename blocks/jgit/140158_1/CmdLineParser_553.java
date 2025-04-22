
package org.eclipse.jgit.pgm.opt;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheIterator;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.WorkingTreeOptions;
import org.eclipse.jgit.util.FS;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class AbstractTreeIteratorHandler extends
		OptionHandler<AbstractTreeIterator> {
	private final org.eclipse.jgit.pgm.opt.CmdLineParser clp;

	public AbstractTreeIteratorHandler(final CmdLineParser parser
			final OptionDef option
			final Setter<? super AbstractTreeIterator> setter) {
		super(parser
		clp = (org.eclipse.jgit.pgm.opt.CmdLineParser) parser;
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		final String name = params.getParameter(0);

		if (new File(name).isDirectory()) {
			setter.addValue(new FileTreeIterator(
				new File(name)
				FS.DETECTED
				clp.getRepository().getConfig().get(WorkingTreeOptions.KEY)));
			return 1;
		}

		if (new File(name).isFile()) {
			final DirCache dirc;
			try {
				dirc = DirCache.read(new File(name)
			} catch (IOException e) {
				throw new CmdLineException(clp
			}
			setter.addValue(new DirCacheIterator(dirc));
			return 1;
		}

		final ObjectId id;
		try {
			id = clp.getRepository().resolve(name);
		} catch (IOException e) {
			throw new CmdLineException(clp
		}
		if (id == null)
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().notATree)

		final CanonicalTreeParser p = new CanonicalTreeParser();
		try (ObjectReader curs = clp.getRepository().newObjectReader()) {
			p.reset(curs
		} catch (MissingObjectException | IncorrectObjectTypeException e) {
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().notATree)
		} catch (IOException e) {
			throw new CmdLineException(clp
					CLIText.format(CLIText.get().cannotReadBecause)
					e.getMessage());
		}

		setter.addValue(p);
		return 1;
	}

	@Override
	public String getDefaultMetaVariable() {
		return CLIText.get().metaVar_treeish;
	}
}
