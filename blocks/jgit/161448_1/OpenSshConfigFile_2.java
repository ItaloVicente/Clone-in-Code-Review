package org.eclipse.jgit.transport.sshd;

import java.io.File;
import java.nio.file.Path;
import java.security.KeyPair;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.transport.SshConfigStore;
import org.eclipse.jgit.util.StringUtils;

public class SshdSessionFactoryBuilder {

	private final State state = new State();

	public SshdSessionFactoryBuilder setProxyDataFactory(
			ProxyDataFactory proxyDataFactory) {
		this.state.proxyDataFactory = proxyDataFactory;
		return this;
	}

	public SshdSessionFactoryBuilder setHomeDirectory(File homeDirectory) {
		this.state.homeDirectory = homeDirectory;
		return this;
	}

	public SshdSessionFactoryBuilder setSshDirectory(File sshDirectory) {
		this.state.sshDirectory = sshDirectory;
		return this;
	}

	public SshdSessionFactoryBuilder setPreferredAuthentications(
			String authentications) {
		this.state.preferredAuthentications = authentications;
		return this;
	}

	public SshdSessionFactoryBuilder setConfigFile(
			Function<File
		this.state.configFileFinder = supplier;
		return this;
	}

	@FunctionalInterface
	public interface ConfigStoreFactory {

		SshConfigStore create(@NonNull File homeDir
				String localUserName);
	}

	public SshdSessionFactoryBuilder setConfigStoreFactory(
			ConfigStoreFactory factory) {
		this.state.configFactory = factory;
		return this;
	}

	public SshdSessionFactoryBuilder setDefaultKnownHostsFiles(
			Function<File
		this.state.knownHostsFileFinder = supplier;
		return this;
	}

	public SshdSessionFactoryBuilder setDefaultIdentities(
			Function<File
		this.state.defaultKeyFileFinder = supplier;
		return this;
	}

	public SshdSessionFactoryBuilder setDefaultKeysProvider(
			Function<File
		this.state.defaultKeysProvider = provider;
		return this;
	}

	public SshdSessionFactoryBuilder setServerKeyDatabase(
			BiFunction<File
		this.state.serverKeyDatabaseCreator = factory;
		return this;
	}

	public SshdSessionFactory build(KeyCache cache) {
		return state.copy().build(cache);
	}

	private static class State {

		ProxyDataFactory proxyDataFactory;

		File homeDirectory;

		File sshDirectory;

		String preferredAuthentications;

		Function<File

		ConfigStoreFactory configFactory;

		Function<File

		Function<File

		Function<File

		BiFunction<File

		State copy() {
			State c = new State();
			c.proxyDataFactory = proxyDataFactory;
			c.homeDirectory = homeDirectory;
			c.sshDirectory = sshDirectory;
			c.preferredAuthentications = preferredAuthentications;
			c.configFileFinder = configFileFinder;
			c.configFactory = configFactory;
			c.knownHostsFileFinder = knownHostsFileFinder;
			c.defaultKeyFileFinder = defaultKeyFileFinder;
			c.defaultKeysProvider = defaultKeysProvider;
			c.serverKeyDatabaseCreator = serverKeyDatabaseCreator;
			return c;
		}

		SshdSessionFactory build(KeyCache cache) {
			SshdSessionFactory factory = new SessionFactory(cache
					proxyDataFactory);
			factory.setHomeDirectory(homeDirectory);
			factory.setSshDirectory(sshDirectory);
			return factory;
		}

		private class SessionFactory extends SshdSessionFactory {

			public SessionFactory(KeyCache cache
					ProxyDataFactory proxyDataFactory) {
				super(cache
			}

			@Override
			protected File getSshConfig(File sshDir) {
				if (configFileFinder != null) {
					return configFileFinder.apply(sshDir);
				}
				return super.getSshConfig(sshDir);
			}

			@Override
			protected List<Path> getDefaultKnownHostsFiles(File sshDir) {
				if (knownHostsFileFinder != null) {
					List<Path> result = knownHostsFileFinder.apply(sshDir);
					return result == null ? Collections.emptyList() : result;
				}
				return super.getDefaultKnownHostsFiles(sshDir);
			}

			@Override
			protected List<Path> getDefaultIdentities(File sshDir) {
				if (defaultKeyFileFinder != null) {
					List<Path> result = defaultKeyFileFinder.apply(sshDir);
					return result == null ? Collections.emptyList() : result;
				}
				return super.getDefaultIdentities(sshDir);
			}

			@Override
			protected String getDefaultPreferredAuthentications() {
				if (!StringUtils.isEmptyOrNull(preferredAuthentications)) {
					return preferredAuthentications;
				}
				return super.getDefaultPreferredAuthentications();
			}

			@Override
			protected Iterable<KeyPair> getDefaultKeys(File sshDir) {
				if (defaultKeysProvider != null) {
					Iterable<KeyPair> result = defaultKeysProvider
							.apply(sshDir);
					return result == null ? Collections.emptyList() : result;
				}
				return super.getDefaultKeys(sshDir);
			}

			@Override
			protected ServerKeyDatabase createServerKeyDatabase(File homeDir
					File sshDir) {
				if (serverKeyDatabaseCreator != null) {
					ServerKeyDatabase result = serverKeyDatabaseCreator
							.apply(homeDir
					if (result != null) {
						return result;
					}
				}
				return super.createServerKeyDatabase(homeDir
			}

			@Override
			protected SshConfigStore createSshConfigStore(File homeDir
					File configFile
				if (configFactory != null) {
					return configFactory.create(homeDir
							localUserName);
				}
				return super.createSshConfigStore(homeDir
						localUserName);
			}
		}
	}
}
