
package org.eclipse.jgit.junit;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.junit.Assert;
import org.junit.Test;

public abstract class JGitTestUtil {
	public static final String CLASSPATH_TO_RESOURCES = "org/eclipse/jgit/test/resources/";

	private JGitTestUtil() {
		throw new UnsupportedOperationException();
	}

	public static String getName() {
		GatherStackTrace stack;
		try {
			throw new GatherStackTrace();
		} catch (GatherStackTrace wanted) {
			stack = wanted;
		}

		try {
			for (StackTraceElement stackTrace : stack.getStackTrace()) {
				String className = stackTrace.getClassName();
				String methodName = stackTrace.getMethodName();
				Method method;
				try {
							.getMethod(methodName
				} catch (NoSuchMethodException e) {
					continue;
				}

				Test annotation = method.getAnnotation(Test.class);
				if (annotation != null)
					return methodName;
			}
		} catch (ClassNotFoundException shouldNeverOccur) {
		}

		throw new AssertionError("Cannot determine name of current test");
	}

	@SuppressWarnings("serial")
	private static class GatherStackTrace extends Exception {
	}

	public static void assertEquals(byte[] exp
		Assert.assertEquals(s(exp)
	}

	private static String s(byte[] raw) {
		return RawParseUtils.decode(raw);
	}

	public static File getTestResourceFile(String fileName) {
		if (fileName == null || fileName.length() <= 0) {
			return null;
		}
		final URL url = cl().getResource(CLASSPATH_TO_RESOURCES + fileName);
		if (url == null) {
			return new File("tst"
		}
		if ("jar".equals(url.getProtocol())) {
			try {
				File tmp = File.createTempFile("tmp_"
				copyTestResource(fileName
				return tmp;
			} catch (IOException err) {
				throw new RuntimeException("Cannot create temporary file"
			}
		}
		try {
			return new File(url.toURI());
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage() + " " + url);
		} catch (URISyntaxException e) {
			return new File(url.getPath());
		}
	}

	public static void copyTestResource(String name
			throws IOException {
		URL url = cl().getResource(CLASSPATH_TO_RESOURCES + name);
		if (url == null)
			throw new FileNotFoundException(name);
		try (InputStream in = url.openStream();
				FileOutputStream out = new FileOutputStream(dest)) {
			byte[] buf = new byte[4096];
			for (int n; (n = in.read(buf)) > 0;)
				out.write(buf
		}
	}

	private static ClassLoader cl() {
		return JGitTestUtil.class.getClassLoader();
	}

	public static File writeTrashFile(final Repository db
			final String name
		File path = new File(db.getWorkTree()
		write(path
		return path;
	}

	public static File writeTrashFile(final Repository db
			final String subdir
			final String name
		File path = new File(db.getWorkTree() + "/" + subdir
		write(path
		return path;
	}

	public static void write(File f
			throws IOException {
		FileUtils.mkdirs(f.getParentFile()
		try (Writer w = new OutputStreamWriter(new FileOutputStream(f)
				UTF_8)) {
			w.write(body);
		}
	}

	public static String read(File file) throws IOException {
		final byte[] body = IO.readFully(file);
		return new String(body
	}

	public static String read(Repository db
			throws IOException {
		File file = new File(db.getWorkTree()
		return read(file);
	}

	public static boolean check(Repository db
		File file = new File(db.getWorkTree()
		return file.exists();
	}

	public static void deleteTrashFile(final Repository db
			final String name) throws IOException {
		File path = new File(db.getWorkTree()
		FileUtils.delete(path);
	}

	public static Path writeLink(Repository db
			String target) throws Exception {
		return FileUtils.createSymLink(new File(db.getWorkTree()
				target);
	}

	public static byte[] concat(byte[]... b) {
		int n = 0;
		for (byte[] a : b) {
			n += a.length;
		}

		byte[] data = new byte[n];
		n = 0;
		for (byte[] a : b) {
			System.arraycopy(a
			n += a.length;
		}
		return data;
	}
}
