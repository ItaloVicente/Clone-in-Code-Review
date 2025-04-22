package org.eclipse.jgit.archive;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.ArchiveCommand;

public class ArchiveFormats {
	private static final List<String> myFormats = new ArrayList<>();

	private static final void register(String name
		myFormats.add(name);
		ArchiveCommand.registerFormat(name
	}

	public static void registerAll() {
		register("tar"
		register("tgz"
		register("tbz2"
		register("txz"
		register("zip"
	}

	public static void unregisterAll() {
		for (String name : myFormats) {
			ArchiveCommand.unregisterFormat(name);
		}
		myFormats.clear();
	}
}
