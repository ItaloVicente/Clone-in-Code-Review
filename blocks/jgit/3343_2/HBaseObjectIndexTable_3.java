
package org.eclipse.jgit.storage.hbase;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;

import org.apache.hadoop.conf.Configuration;
import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.spi.util.ExecutorTools;
import org.eclipse.jgit.transport.URIish;

public class HBaseDatabaseBuilder {
	private String hosts;

	private String schemaPrefix;

	private Configuration configuration;

	private ExecutorService executorService;

	public HBaseDatabaseBuilder setURI(final String url)
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
		if (endPrefix < 0)
			endPrefix = path.length();
		setSchemaPrefix(path.substring(0
		return this;
	}

	public String getSchemaPrefix() {
		return schemaPrefix;
	}

	public HBaseDatabaseBuilder setSchemaPrefix(String schemaPrefix) {
		if (schemaPrefix != null && 0 < schemaPrefix.length())
			this.schemaPrefix = schemaPrefix;
		else
			this.schemaPrefix = null;
		return this;
	}

	public String getHosts() {
		return hosts;
	}

	public HBaseDatabaseBuilder setHosts(String hosts) {
		this.hosts = hosts;
		this.configuration = null;
		return this;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public HBaseDatabaseBuilder setConfiguration(Configuration conf) {
		this.configuration = conf;
		return this;
	}

	public ExecutorService getExecutorService() {
		return executorService;
	}

	public HBaseDatabaseBuilder setExecutorService(
			ExecutorService executorService) {
		this.executorService = executorService;
		return this;
	}

	public HBaseDatabaseBuilder setup() {
		if (configuration == null) {
			configuration = new Configuration();
			if (getHosts() != null){
				configuration.set("hbase.zookeeper.quorum"
				configuration.setInt("hbase.zookeeper.property.clientPort"
			}
		}

		if (executorService == null)
			executorService = ExecutorTools.getDefaultExecutorService();

		return this;
	}

	public HBaseDatabase build() throws DhtException {
		return new HBaseDatabase(setup());
	}
}
