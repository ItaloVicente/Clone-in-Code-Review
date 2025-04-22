
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.CommandFailedException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FS_Win32_Cygwin extends FS_Win32 {
	private final static Logger LOG = LoggerFactory
			.getLogger(FS_Win32_Cygwin.class);

	private static String cygpath;

	public static boolean isCygwin() {
		final String path = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					@Override
					public String run() {
					}
				});
		if (path == null)
			return false;
		File found = FS.searchPath(path
		if (found != null)
			cygpath = found.getPath();
		return cygpath != null;
	}

	public FS_Win32_Cygwin() {
		super();
	}

	protected FS_Win32_Cygwin(FS src) {
		super(src);
	}

	@Override
	public FS newInstance() {
		return new FS_Win32_Cygwin(this);
	}

	@Override
	public File resolve(File dir
			String w;
			try {
				w = readPipe(dir
					new String[] { cygpath
					UTF_8.name());
			} catch (CommandFailedException e) {
				LOG.warn(e.getMessage());
				return null;
			}
			if (!StringUtils.isEmptyOrNull(w)) {
				return new File(w);
			}
		}
		return super.resolve(dir
	}

	@Override
	protected File userHomeImpl() {
		final String home = AccessController
				.doPrivileged(new PrivilegedAction<String>() {
					@Override
					public String run() {
					}
				});
		if (home == null || home.length() == 0)
			return super.userHomeImpl();
		return resolve(new File(".")
	}

	@Override
	public ProcessBuilder runInShell(String cmd
		List<String> argv = new ArrayList<>(4 + args.length);
		argv.add(cmd);
		argv.addAll(Arrays.asList(args));
		ProcessBuilder proc = new ProcessBuilder();
		proc.command(argv);
		return proc;
	}

	@Override
	public String relativize(String base
		final String relativized = super.relativize(base
		return relativized.replace(File.separatorChar
	}

	@Override
	public ProcessResult runHookIfPresent(Repository repository
			String[] args
			String stdinArgs) throws JGitInternalException {
		return internalRunHookIfPresent(repository
				errRedirect
	}

	@Override
	public File findHook(Repository repository
		final File gitdir = repository.getDirectory();
		if (gitdir == null) {
			return null;
		}
		final Path hookPath = gitdir.toPath().resolve(Constants.HOOKS)
				.resolve(hookName);
		if (Files.isExecutable(hookPath))
			return hookPath.toFile();
		return null;
	}
}
