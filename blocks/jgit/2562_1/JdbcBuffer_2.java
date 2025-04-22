package org.eclipse.jgit.storage.dht.spi.jdbc;

import java.sql.Driver;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.eclipse.jgit.storage.dht.DhtException;
import org.eclipse.jgit.storage.dht.DhtTimeoutException;
import org.eclipse.jgit.storage.dht.Timeout;

public class ClientPool {
	public static class Builder {
		private Driver driver;

		private String url;

		private Properties properties = new Properties();

		private Timeout connectionTimeout = Timeout.seconds(5);

		private int maxOpen = 10;

		private int maxIdle = 5;

		public Builder setDriver(Driver driver) {
			this.driver = driver;
			return this;
		}

		public Builder setDriver(Class<? extends Driver> clazz)
				throws DhtException {
			try {
				return setDriver(clazz.newInstance());
			} catch (InstantiationException err) {
				throw new DhtException(err);
			} catch (IllegalAccessException err) {
				throw new DhtException(err);
			}
		}

		@SuppressWarnings("unchecked")
		public Builder setDriver(String className) throws DhtException {
			try {
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				Class<?> clazz = Class.forName(className
				return setDriver((Class<? extends Driver>) clazz);
			} catch (ClassNotFoundException e) {
				throw new DhtException(e);
			}
		}

		public Builder setURL(String url) {
			this.url = url;
			return this;
		}

		public Properties getProperties() {
			return properties;
		}

		public Builder setUser(String user) {
			return setProperty("user"
		}

		public Builder setPassword(String password) {
			return setProperty("password"
		}

		public Builder setProperty(String key
			properties.put(key
			return this;
		}

		public Builder setProperties(Map<String
			properties.clear();
			properties.putAll(map);
			return this;
		}

		public Builder setMaxOpen(int open) {
			maxOpen = open;
			return this;
		}

		public Builder setMaxIdle(int idle) {
			maxIdle = idle;
			return this;
		}

		public Builder setConnectionTimeout(Timeout timeout) {
			this.connectionTimeout = timeout;
			return this;
		}

		public ClientPool build() throws DhtException {
			return new ClientPool(this);
		}
	}

	private final Driver driver;

	private final String url;

	private final Properties properties;

	private final Timeout connectionTimeout;

	private final Semaphore clients;

	private final ArrayBlockingQueue<Client> idle;

	private ClientPool(Builder info) throws DhtException {
		driver = info.driver;
		url = info.url;
		properties = new Properties(info.properties);
		connectionTimeout = info.connectionTimeout;
		clients = new Semaphore(Math.max(1
		idle = new ArrayBlockingQueue<Client>(Math.max(1

		idle.add(newClient());
	}

	public void shutdown() {
		while (!idle.isEmpty()) {
			Client conn = idle.poll();
			if (conn != null) {
				try {
					conn.close();
				} catch (DhtException err) {
				}
			}
		}
	}

	public Client get() throws DhtException {
		try {
			long time = connectionTimeout.getTime();
			TimeUnit unit = connectionTimeout.getUnit();
			if (!clients.tryAcquire(time
				throw new DhtTimeoutException(MessageFormat.format(
						JdbcText.get().connectionTimeout
			}
		} catch (InterruptedException interrupt) {
			throw new DhtTimeoutException(interrupt);
		}

		Client conn = idle.poll();
		if (conn == null)
			conn = newClient();
		return conn;
	}

	public void release(Client conn) {
		try {
			if (conn.hasPending())
				conn.clear();
			if (conn.hasPending() || !idle.offer(conn)) {
				try {
					conn.close();
				} catch (DhtException err) {
				}
			}
		} finally {
			clients.release();
		}
	}

	private Client newClient() throws DhtException {
		try {
			return new Client(driver.connect(url
		} catch (SQLException err) {
			throw new DhtException(JdbcText.get().connectionFailed
		}
	}
}
