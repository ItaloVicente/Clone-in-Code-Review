
package org.eclipse.jgit.pgm;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.awtui.AwtAuthenticator;
import org.eclipse.jgit.awtui.AwtCredentialsProvider;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lfs.BuiltinLFS;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.pgm.opt.CmdLineParser;
import org.eclipse.jgit.pgm.opt.SubcommandHandler;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.http.apache.HttpClientConnectionFactory;
import org.eclipse.jgit.util.CachedAuthenticator;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.OptionHandlerFilter;

public class Main {
	@Option(name = "--help"
	private boolean help;

	@Option(name = "--version"
	private boolean version;

	@Option(name = "--show-stack-trace"
	private boolean showStackTrace;

	@Option(name = "--git-dir"
	private String gitdir;

	@Argument(index = 0
	private TextBuiltin subcommand;

	@Argument(index = 1
	private List<String> arguments = new ArrayList<>();

	PrintWriter writer;

	private ExecutorService gcExecutor;

	public Main() {
		HttpTransport.setConnectionFactory(new HttpClientConnectionFactory());
		BuiltinLFS.register();
		gcExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
			private final ThreadFactory baseFactory = Executors
					.defaultThreadFactory();

			@Override
			public Thread newThread(Runnable taskBody) {
				Thread thr = baseFactory.newThread(taskBody);
				return thr;
			}
		});
	}

	public static void main(String[] argv) throws Exception {
		BuiltinLFS.register();

		new Main().run(argv);
	}

	protected void run(String[] argv) throws Exception {
		writer = createErrorWriter();
		try {
			if (!installConsole()) {
				AwtAuthenticator.install();
				AwtCredentialsProvider.install();
			}
			configureHttpProxy();
			execute(argv);
		} catch (Die err) {
			if (err.isAborted()) {
				exit(1
			}
			writer.println(CLIText.fatalError(err.getMessage()));
			if (showStackTrace) {
				err.printStackTrace(writer);
			}
			exit(128
		} catch (Exception err) {
			if (err.getClass() == IOException.class) {
					exit(0
				}
					exit(0
				}
			}
			if (!showStackTrace && err.getCause() != null
					&& err instanceof TransportException) {
				writer.println(CLIText.fatalError(err.getCause().getMessage()));
			}

				writer.println(CLIText.fatalError(err.getMessage()));
				if (showStackTrace) {
					err.printStackTrace();
				}
				exit(128
			}
			err.printStackTrace();
			exit(1
		}
		if (System.out.checkError()) {
			writer.println(CLIText.get().unknownIoErrorStdout);
			exit(1
		}
		if (writer.checkError()) {
			exit(1
		}
		gcExecutor.shutdown();
		gcExecutor.awaitTermination(10
	}

	PrintWriter createErrorWriter() {
		return new PrintWriter(new OutputStreamWriter(System.err
	}

	private void execute(String[] argv) throws Exception {
		final CmdLineParser clp = new SubcommandLineParser(this);

		try {
			clp.parseArgument(argv);
		} catch (CmdLineException err) {
			if (argv.length > 0 && !help && !version) {
				writer.println(CLIText.fatalError(err.getMessage()));
				writer.flush();
				exit(1
			}
		}

		if (argv.length == 0 || help) {
			final String ex = clp.printExample(OptionHandlerFilter.ALL
					CLIText.get().resourceBundle());
			if (help) {
				writer.println();
				clp.printUsage(writer
				writer.println();
			} else if (subcommand == null) {
				writer.println();
				writer.println(CLIText.get().mostCommonlyUsedCommandsAre);
				final CommandRef[] common = CommandCatalog.common();
				int width = 0;
				for (CommandRef c : common) {
					width = Math.max(width
				}
				width += 2;

				for (CommandRef c : common) {
					writer.print(' ');
					writer.print(c.getName());
					for (int i = c.getName().length(); i < width; i++) {
						writer.print(' ');
					}
					writer.print(CLIText.get().resourceBundle().getString(c.getUsage()));
					writer.println();
				}
				writer.println();
			}
			writer.flush();
			exit(1
		}

		if (version) {
			String cmdId = Version.class.getSimpleName()
					.toLowerCase(Locale.ROOT);
			subcommand = CommandCatalog.get(cmdId).create();
		}

		final TextBuiltin cmd = subcommand;
		init(cmd);
		try {
			cmd.execute(arguments.toArray(new String[0]));
		} finally {
			if (cmd.outw != null) {
				cmd.outw.flush();
			}
			if (cmd.errw != null) {
				cmd.errw.flush();
			}
		}
	}

	void init(TextBuiltin cmd) throws IOException {
		if (cmd.requiresRepository()) {
			cmd.init(openGitDir(gitdir)
		} else {
			cmd.init(null
		}
	}

	void exit(int status
		writer.flush();
		System.exit(status);
	}

	protected Repository openGitDir(String aGitdir) throws IOException {
				.findGitDir();
		if (rb.getGitDir() == null)
			throw new Die(CLIText.get().cantFindGitDirectory);
		return rb.build();
	}

	private static boolean installConsole() {
		try {
			return true;
		} catch (ClassNotFoundException | NoClassDefFoundError | UnsupportedClassVersionError e) {
			return false;
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		} catch (SecurityException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		} catch (IllegalAccessException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		} catch (InvocationTargetException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(CLIText.get().cannotSetupConsole
		}
	}

	private static void install(String name)
			throws IllegalAccessException
			NoSuchMethodException
		try {
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof RuntimeException)
				throw (RuntimeException) e.getCause();
			if (e.getCause() instanceof Error)
				throw (Error) e.getCause();
			throw e;
		}
	}

	static void configureHttpProxy() throws MalformedURLException {
		for (String protocol : new String[] { "http"
				continue;
			}
			}
				continue;
			}

			final URL u = new URL(
				throw new MalformedURLException(MessageFormat.format(
						CLIText.get().invalidHttpProxyOnlyHttpSupported

			final String proxyHost = u.getHost();
			final int proxyPort = u.getPort();

			System.setProperty(protocol + ".proxyHost"
			if (proxyPort > 0)
				System.setProperty(protocol + ".proxyPort"
						String.valueOf(proxyPort));

			final String userpass = u.getUserInfo();
				final int c = userpass.indexOf(':');
				final String user = userpass.substring(0
				final String pass = userpass.substring(c + 1);
				CachedAuthenticator.add(
						new CachedAuthenticator.CachedAuthentication(proxyHost
								proxyPort
			}
		}
	}

	static class SubcommandLineParser extends CmdLineParser {
		public SubcommandLineParser(Object bean) {
			super(bean);
		}

		@Override
		protected boolean containsHelp(String... args) {
			return false;
		}
	}
}
