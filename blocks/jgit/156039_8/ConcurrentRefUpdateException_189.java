package org.eclipse.jgit.niofs.internal.op.commands;

import java.io.IOException;
import java.util.function.Consumer;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;

public class WriteConfiguration {

	private final Repository repo;
	private final Consumer<StoredConfig> consumer;

	public WriteConfiguration(final Repository repo
		this.repo = repo;
		this.consumer = consumer;
	}

	public void execute() {
		final StoredConfig cfg = repo.getConfig();
		consumer.accept(cfg);
		try {
			cfg.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
