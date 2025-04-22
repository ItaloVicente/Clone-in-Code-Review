package org.eclipse.jgit.junit;

import static java.lang.ClassLoader.getSystemClassLoader;

import java.net.URL;
import java.net.URLClassLoader;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class SeparateClassloaderTestRunner extends BlockJUnit4ClassRunner {

	public SeparateClassloaderTestRunner(Class<?> klass)
			throws InitializationError {
		super(loadNewClass(klass));
	}

	private static Class<?> loadNewClass(Class<?> klass)
			throws InitializationError {
		try {
			URL[] urls = ((URLClassLoader) getSystemClassLoader()).getURLs();
			ClassLoader testClassLoader = new URLClassLoader(urls) {

				@Override
				public Class<?> loadClass(String name)
						throws ClassNotFoundException {
					if (name.startsWith("org.eclipse.jgit.")) {
						return super.findClass(name);
					}

					return super.loadClass(name);
				}
			};
			return Class.forName(klass.getName()
		} catch (ClassNotFoundException e) {
			throw new InitializationError(e);
		}
	}
}
