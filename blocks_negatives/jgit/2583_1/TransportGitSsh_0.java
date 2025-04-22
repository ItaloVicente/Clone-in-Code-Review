	private abstract class Connection {
		abstract void exec(String commandName) throws TransportException;

		abstract void connect() throws TransportException;

		abstract InputStream getInputStream() throws IOException;

		abstract OutputStream getOutputStream() throws IOException;

		abstract InputStream getErrorStream() throws IOException;

		abstract int getExitStatus();

		abstract void close();
	}

	private class JschConnection extends Connection {
