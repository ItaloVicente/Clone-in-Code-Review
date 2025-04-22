package org.eclipse.egit.core.attributes;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.api.errors.FilterFailedException;
import org.eclipse.jgit.attributes.FilterCommand;
import org.eclipse.jgit.attributes.FilterCommandRegistry;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.eclipse.jgit.util.RawParseUtils;
import org.eclipse.jgit.util.TemporaryBuffer;
import org.eclipse.jgit.util.TemporaryBuffer.LocalFile;

public class Filtering {

	private static final int MAX_EXCEPTION_TEXT_SIZE = 10 * 1024;

	public static InputStream filter(Repository repository, String path,
			InputStream raw, String command) throws IOException {
		if (command == null || command.isEmpty()) {
			return raw;
		}
		if (FilterCommandRegistry.isRegistered(command)) {
			return runInternalFilter(repository, raw, command);
		} else {
			return runExternalFilter(repository, path, raw, command);
		}
	}

	private static InputStream runExternalFilter(Repository repository,
			String path, InputStream raw, String command) throws IOException {
		FS fs = repository.getFS();
		ProcessBuilder filterProcessBuilder = fs.runInShell(command,
				new String[0]);
		filterProcessBuilder.directory(!repository.isBare()
				? repository.getWorkTree() : repository.getDirectory());
		filterProcessBuilder.environment().put(Constants.GIT_DIR_KEY,
				repository.getDirectory().getAbsolutePath());
		ExecutionResult result;
		int rc;
		try {
			result = fs.execute(filterProcessBuilder, raw);
			rc = result.getRc();
		} catch (IOException | InterruptedException e) {
			throw new IOException(new FilterFailedException(e, command, path));
		}
		if (rc != 0) {
			throw new IOException(new FilterFailedException(rc, command, path,
					result.getStdout().toByteArray(MAX_EXCEPTION_TEXT_SIZE),
					RawParseUtils.decode(result.getStderr()
							.toByteArray(MAX_EXCEPTION_TEXT_SIZE))));
		}
		return result.getStdout().openInputStream();
	}

	private static InputStream runInternalFilter(Repository repository,
			InputStream raw, String command) throws IOException {
		LocalFile buffer = new TemporaryBuffer.LocalFile(null);
		FilterCommand filter = FilterCommandRegistry
				.createFilterCommand(command, repository, raw, buffer);
		while (filter.run() != -1) {
		}
		return buffer.openInputStream();
	}

}
