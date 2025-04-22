package org.eclipse.jgit.pgm;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.eclipse.jgit.errors.ConfigInvalidException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;
import org.kohsuke.args4j.Option;

@Command(common = true
class Config extends TextBuiltin {
	@Option(name = "--system")
	private boolean system;

	@Option(name = "--global")
	private boolean global;

	@Option(name = "--local")
	private boolean local;

	@Option(name = "--list"
	private boolean list;

	@Option(name = "--file"
	private File configFile;

	@Override
	protected void run() throws Exception {
		if (list)
			list();
		else
			throw new NotSupportedException(
					"only --list option is currently supported");
	}

	private void list() throws IOException
		final FS fs = getRepository().getFS();
		if (configFile != null) {
			list(new FileBasedConfig(configFile
			return;
		}
		if (system || isListAll())
			list(SystemReader.getInstance().openSystemConfig(null
		if (global || isListAll())
			list(SystemReader.getInstance().openUserConfig(null
		if (local || isListAll())
			list(new FileBasedConfig(fs.resolve(getRepository().getDirectory()
					Constants.CONFIG)
	}

	private boolean isListAll() {
		return !system && !global && !local && configFile == null;
	}

	private void list(StoredConfig config) throws IOException
			ConfigInvalidException {
		config.load();
		Set<String> sections = config.getSections();
		for (String section : sections) {
			Set<String> names = config.getNames(section);
			for (String name : names) {
				for (String value : config.getStringList(section
					out.println(section + "." + name + "=" + value);
			}
			if (names.isEmpty()) {
				for (String subsection : config.getSubsections(section)) {
					names = config.getNames(section
					for (String name : names) {
						for (String value : config.getStringList(section
								subsection
							out.println(section + "." + subsection + "."
									+ name + "=" + value);
					}
				}
			}
		}
	}
}
