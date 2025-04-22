
package org.eclipse.jgit.storage.hbase;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.DhtRepository;
import org.eclipse.jgit.storage.dht.DhtRepositoryBuilder;
import org.eclipse.jgit.transport.URIish;

public class HBaseRepositoryBuilder
		extends
		DhtRepositoryBuilder<HBaseRepositoryBuilder
	private String hosts;

	private String schemaPrefix;

	private ExecutorService executorService;

	public HBaseRepositoryBuilder setURI(final String url)
			throws URISyntaxException {
		URIish u = new URIish(url);
		if (!"git+hbase".equals(u.getScheme()))
			throw new IllegalArgumentException();

		String host = u.getHost();
		if (host != null)
			setHosts(host);

		String path = u.getPath();
		if (path.startsWith("/"))
			path = path.substring(1);

		int endPrefix = path.indexOf('/');
		setSchemaPrefix(path.substring(0
		setRepositoryName(path.substring(endPrefix + 1));
		return self();
	}

	public String getHosts() {
		return hosts;
	}

	public HBaseRepositoryBuilder setHosts(String hosts) {
		this.hosts = hosts;
		return self();
	}

	public String getSchemaPrefix() {
		return schemaPrefix;
	}

	public HBaseRepositoryBuilder setSchemaPrefix(String schemaPrefix) {
		if (schemaPrefix != null && 0 < schemaPrefix.length())
			this.schemaPrefix = schemaPrefix;
		else
			this.schemaPrefix = null;
		return self();
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public HBaseRepositoryBuilder setExecutorService(
			ExecutorService executorService) {
		this.executorService = executorService;
		return self();
	}

	@Override
	public HBaseRepositoryBuilder setup() throws IllegalArgumentException
			DhtException
		if (getDatabase() == null) {
			setDatabase(new HBaseDatabaseBuilder()
					.setHosts(getHosts())
					.setSchemaPrefix(getSchemaPrefix())
					.setExecutorService(getExecutorService()).build());
		}
		return super.setup();
	}
}
