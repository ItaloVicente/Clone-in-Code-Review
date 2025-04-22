
package org.eclipse.jgit.pgm;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_SECTION_I18N;
import static org.eclipse.jgit.lib.ConfigConstants.CONFIG_KEY_LOG_OUTPUT_ENCODING;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.eclipse.jgit.lib.Constants.R_REMOTES;
import static org.eclipse.jgit.lib.Constants.R_TAGS;

import java.io.BufferedWriter;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.internal.SshDriver;
import org.eclipse.jgit.pgm.opt.CmdLineParser;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.sshd.DefaultProxyDataFactory;
import org.eclipse.jgit.transport.sshd.JGitKeyCache;
import org.eclipse.jgit.transport.sshd.SshdSessionFactory;
import org.eclipse.jgit.util.io.ThrowingPrintWriter;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;

public abstract class TextBuiltin {
	private String commandName;

	@Option(name = "--help"
	private boolean help;

	@Option(name = "--ssh"
	private SshDriver sshDriver = SshDriver.JSCH;

	protected InputStream ins;

	protected ThrowingPrintWriter outw;

	protected OutputStream outs;

	protected ThrowingPrintWriter errw;

	protected OutputStream errs;

	protected Repository db;

	protected String gitdir;

	protected RevWalk argWalk;

	final void setCommandName(String name) {
		commandName = name;
	}

	protected boolean requiresRepository() {
		return true;
	}

	public void initRaw(final Repository repository
			InputStream input
		this.ins = input;
		this.outs = output;
		this.errs = error;
		init(repository
	}

	private Charset getLogOutputEncodingCharset(Repository repository) {
		if (repository != null) {
			String logOutputEncoding = repository.getConfig().getString(
					CONFIG_SECTION_I18N
			if (logOutputEncoding != null) {
				try {
					return Charset.forName(logOutputEncoding);
				} catch (IllegalArgumentException e) {
					throw die(CLIText.get().cannotCreateOutputStream
				}
			}
		}
		return UTF_8;
	}

	protected void init(Repository repository
		Charset charset = getLogOutputEncodingCharset(repository);

		if (ins == null)
			ins = new FileInputStream(FileDescriptor.in);
		if (outs == null)
			outs = new FileOutputStream(FileDescriptor.out);
		if (errs == null)
			errs = new FileOutputStream(FileDescriptor.err);
		outw = new ThrowingPrintWriter(new BufferedWriter(
				new OutputStreamWriter(outs
		errw = new ThrowingPrintWriter(new BufferedWriter(
				new OutputStreamWriter(errs

		if (repository != null && repository.getDirectory() != null) {
			db = repository;
			gitdir = repository.getDirectory().getAbsolutePath();
		} else {
			db = repository;
			gitdir = gitDir;
		}
	}

	public final void execute(String[] args) throws Exception {
		parseArguments(args);
		switch (sshDriver) {
		case APACHE: {
			SshdSessionFactory factory = new SshdSessionFactory(
					new JGitKeyCache()
			Runtime.getRuntime()
					.addShutdownHook(new Thread(() -> factory.close()));
			SshSessionFactory.setInstance(factory);
			break;
		}
		case JSCH:
		default:
			SshSessionFactory.setInstance(null);
			break;
		}
		run();
	}

	protected void parseArguments(String[] args) throws IOException {
		final CmdLineParser clp = new CmdLineParser(this);
		help = containsHelp(args);
		try {
			clp.parseArgument(args);
		} catch (CmdLineException err) {
			this.errw.println(CLIText.fatalError(err.getMessage()));
			if (help) {
				printUsage(""
			}
			throw die(true
		}

		if (help) {
			printUsage(""
			throw new TerminatedByHelpException();
		}

		argWalk = clp.getRevWalkGently();
	}

	public void printUsageAndExit(CmdLineParser clp) throws IOException {
		printUsageAndExit(""
	}

	public void printUsageAndExit(String message
		printUsage(message
		throw die(true);
	}

	protected void printUsage(String message
			throws IOException {
		errw.println(message);
		errw.print(commandName);
		clp.printSingleLineUsage(errw
		errw.println();

		errw.println();
		clp.printUsage(errw
		errw.println();

		errw.flush();
	}

	public ThrowingPrintWriter getErrorWriter() {
		return errw;
	}

	public ThrowingPrintWriter getOutputWriter() {
		return outw;
	}

	protected ResourceBundle getResourceBundle() {
		return CLIText.get().resourceBundle();
	}

	protected abstract void run() throws Exception;

	public Repository getRepository() {
		return db;
	}

	ObjectId resolve(String s) throws IOException {
		final ObjectId r = db.resolve(s);
		if (r == null)
			throw die(MessageFormat.format(CLIText.get().notARevision
		return r;
	}

	protected static Die die(String why) {
		return new Die(why);
	}

	protected static Die die(String why
		return new Die(why
	}

	protected static Die die(boolean aborted) {
		return new Die(aborted);
	}

	protected static Die die(boolean aborted
		return new Die(aborted
	}

	String abbreviateRef(String dst
		if (dst.startsWith(R_HEADS))
			dst = dst.substring(R_HEADS.length());
		else if (dst.startsWith(R_TAGS))
			dst = dst.substring(R_TAGS.length());
		else if (abbreviateRemote && dst.startsWith(R_REMOTES))
			dst = dst.substring(R_REMOTES.length());
		return dst;
	}

	public static boolean containsHelp(String[] args) {
		for (String str : args) {
				return true;
			}
		}
		return false;
	}

	public static class TerminatedByHelpException extends Die {
		private static final long serialVersionUID = 1L;

		public TerminatedByHelpException() {
			super(true);
		}

	}
}
