	protected abstract class Connection {
		protected abstract void exec(String commandName)
				throws TransportException;

		protected abstract void connect() throws TransportException;

		protected abstract InputStream getInputStream() throws IOException;

		protected abstract OutputStream getOutputStream() throws IOException;

		protected abstract InputStream getErrorStream() throws IOException;

		protected abstract int getExitStatus();

		protected abstract void close();
