package org.eclipse.jgit.pgm;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ProxyConfigTest {
	private ProcessBuilder processBuilder;

	private Map<String

	@Before
	public void setUp() {
		String separator = System.getProperty("file.separator");
		String classpath = System.getProperty("java.class.path");
		String path = System.getProperty("java.home") + separator + "bin"
				+ separator + "java";
		processBuilder = new ProcessBuilder(path
				ProxyPropertiesDumper.class.getName());
		environment = processBuilder.environment();
		environment.remove("http_proxy");
		environment.remove("https_proxy");
		environment.remove("HTTP_PROXY");
		environment.remove("HTTPS_PROXY");
	}

	@Test
	public void testNoSetting() throws IOException
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: null
				getOutput(start));
	}

	@Test
	public void testhttpProxy() throws IOException
		environment.put("http_proxy"
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: xx
				getOutput(start));
	}

	@Test
	public void testHTTPPROXY() throws IOException
		environment.put("HTTP_PROXY"
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: null
				getOutput(start));
	}

	@Test
	public void testHTTPPROXYBoth() throws IOException
		environment.put("http_proxy"
		environment.put("HTTP_PROXY"
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: xx
				getOutput(start));
	}

	@Test
	public void testhttpsProxy() throws IOException
		environment.put("https_proxy"
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: null
				getOutput(start));
	}

	@Test
	public void testHTTPSPROXY() throws IOException
		environment.put("HTTPS_PROXY"
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: null
				getOutput(start));
	}

	@Test
	public void testHTTPSPROXYBoth() throws IOException
		environment.put("https_proxy"
		environment.put("HTTPS_PROXY"
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: null
				getOutput(start));
	}

	@Test
	public void testAll() throws IOException
		environment.put("http_proxy"
		environment.put("HTTP_PROXY"
		environment.put("https_proxy"
		environment.put("HTTPS_PROXY"
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: xx
				getOutput(start));
	}

	@Test
	public void testDontOverwriteHttp()
			throws IOException
		environment.put("http_proxy"
		environment.put("HTTP_PROXY"
		environment.put("https_proxy"
		environment.put("HTTPS_PROXY"
		List<String> command = processBuilder.command();
		command.add(1
		command.add(2
		command.add("dontClearProperties");
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: gondola
				getOutput(start));
	}

	@Test
	public void testOverwriteHttpPort()
			throws IOException
		environment.put("http_proxy"
		environment.put("HTTP_PROXY"
		environment.put("https_proxy"
		environment.put("HTTPS_PROXY"
		List<String> command = processBuilder.command();
		command.add(1
		command.add("dontClearProperties");
		Process start = processBuilder.start();
		start.waitFor();
		assertEquals(
				"http.proxyHost: xx
				getOutput(start));
	}

	private static String getOutput(Process p)
			throws IOException
		try (InputStream inputStream = p.getInputStream()) {
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = inputStream.read(buffer)) != -1) {
				result.write(buffer
			}
			return result.toString("UTF-8");
		}
	}
}

class ProxyPropertiesDumper {
	public static void main(String args[]) {
		try {
			if (args.length == 0 || !args[0].equals("dontClearProperties")) {
				System.clearProperty("http.proxyHost");
				System.clearProperty("http.proxyPort");
				System.clearProperty("https.proxyHost");
				System.clearProperty("https.proxyPort");
			}
			Main.configureHttpProxy();
			System.out.printf(
					"http.proxyHost: %s
					System.getProperty("http.proxyHost")
					System.getProperty("http.proxyPort")
					System.getProperty("https.proxyHost")
					System.getProperty("https.proxyPort"));
			System.out.flush();
		} catch (MalformedURLException e) {
			System.out.println("exception: " + e);
		}
	}
}
