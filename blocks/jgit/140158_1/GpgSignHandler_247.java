
package org.eclipse.jgit.pgm.opt;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.Die;
import org.eclipse.jgit.pgm.TextBuiltin;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.IllegalAnnotationError;
import org.kohsuke.args4j.NamedOptionDef;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.OptionHandlerRegistry;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.RestOfArgumentsHandler;
import org.kohsuke.args4j.spi.Setter;

public class CmdLineParser extends org.kohsuke.args4j.CmdLineParser {
	static {
		OptionHandlerRegistry registry = OptionHandlerRegistry.getRegistry();
		registry.registerHandler(AbstractTreeIterator.class
				AbstractTreeIteratorHandler.class);
		registry.registerHandler(ObjectId.class
		registry.registerHandler(RefSpec.class
		registry.registerHandler(RevCommit.class
		registry.registerHandler(RevTree.class
		registry.registerHandler(List.class
	}

	private final Repository db;

	private RevWalk walk;

	private boolean seenHelp;

	private TextBuiltin cmd;

	public CmdLineParser(Object bean) {
		this(bean
	}

	public CmdLineParser(Object bean
		super(bean);
		if (bean instanceof TextBuiltin) {
			cmd = (TextBuiltin) bean;
		}
		if (repo == null && cmd != null) {
			repo = cmd.getRepository();
		}
		this.db = repo;
	}

	@Override
	public void parseArgument(String... args) throws CmdLineException {
		final ArrayList<String> tmp = new ArrayList<>(args.length);
		for (int argi = 0; argi < args.length; argi++) {
			final String str = args[argi];
				while (argi < args.length)
					tmp.add(args[argi++]);
				break;
			}

				final int eq = str.indexOf('=');
				if (eq > 0) {
					tmp.add(str.substring(0
					tmp.add(str.substring(eq + 1));
					continue;
				}
			}

			tmp.add(str);

			if (containsHelp(args)) {
				seenHelp = true;
				break;
			}
		}
		List<OptionHandler> backup = null;
		if (seenHelp) {
			backup = unsetRequiredOptions();
		}

		try {
			super.parseArgument(tmp.toArray(new String[0]));
		} catch (Die e) {
			if (!seenHelp) {
				throw e;
			}
			printToErrorWriter(CLIText.fatalError(e.getMessage()));
		} finally {
			if (backup != null && !backup.isEmpty()) {
				restoreRequiredOptions(backup);
			}
			seenHelp = false;
		}
	}

	private void printToErrorWriter(String error) {
		if (cmd == null) {
			System.err.println(error);
		} else {
			try {
				cmd.getErrorWriter().println(error);
			} catch (IOException e1) {
				System.err.println(error);
			}
		}
	}

	private List<OptionHandler> unsetRequiredOptions() {
		List<OptionHandler> options = getOptions();
		List<OptionHandler> backup = new ArrayList<>(options);
		for (Iterator<OptionHandler> iterator = options.iterator(); iterator
				.hasNext();) {
			OptionHandler handler = iterator.next();
			if (handler.option instanceof NamedOptionDef
					&& handler.option.required()) {
				iterator.remove();
			}
		}
		return backup;
	}

	private void restoreRequiredOptions(List<OptionHandler> backup) {
		List<OptionHandler> options = getOptions();
		options.clear();
		options.addAll(backup);
	}

	protected boolean containsHelp(String... args) {
		return TextBuiltin.containsHelp(args);
	}

	public Repository getRepository() {
		if (db == null)
			throw new IllegalStateException(CLIText.get().noGitRepositoryConfigured);
		return db;
	}

	public RevWalk getRevWalk() {
		if (walk == null)
			walk = new RevWalk(getRepository());
		return walk;
	}

	public RevWalk getRevWalkGently() {
		return walk;
	}

	class MyOptionDef extends OptionDef {

		public MyOptionDef(OptionDef o) {
			super(o.usage()
					o.handler()
		}

		@Override
		public String toString() {
			if (metaVar() == null)
			try {
				Field field = CLIText.class.getField(metaVar());
				String ret = field.get(CLIText.get()).toString();
				return ret;
			} catch (Exception e) {
				e.printStackTrace(System.err);
				return metaVar();
			}
		}

		@Override
		public boolean required() {
			return seenHelp ? false : super.required();
		}
	}

	@Override
	protected OptionHandler createOptionHandler(OptionDef o
		if (o instanceof NamedOptionDef)
			return super.createOptionHandler(o
		else
			return super.createOptionHandler(new MyOptionDef(o)

	}

	@Override
	public void printSingleLineUsage(Writer w
		List<OptionHandler> options = getOptions();
		if (options.isEmpty()) {
			super.printSingleLineUsage(w
			return;
		}
		List<OptionHandler> backup = new ArrayList<>(options);
		boolean changed = sortRestOfArgumentsHandlerToTheEnd(options);
		try {
			super.printSingleLineUsage(w
		} finally {
			if (changed) {
				options.clear();
				options.addAll(backup);
			}
		}
	}

	private boolean sortRestOfArgumentsHandlerToTheEnd(
			List<OptionHandler> options) {
		for (int i = 0; i < options.size(); i++) {
			OptionHandler handler = options.get(i);
			if (handler instanceof RestOfArgumentsHandler
					|| handler instanceof PathTreeFilterHandler) {
				options.remove(i);
				options.add(handler);
				return true;
			}
		}
		return false;
	}
}
