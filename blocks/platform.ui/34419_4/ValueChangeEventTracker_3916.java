
package org.eclipse.jface.databinding.conformance.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.eclipse.jface.databinding.conformance.delegate.IObservableContractDelegate;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SuiteBuilder {
	private LinkedHashSet content;

	public SuiteBuilder() {
		content = new LinkedHashSet();
	}

	public SuiteBuilder addTests(Class testCase) {
		content.add(testCase);
		return this;
	}

	public SuiteBuilder addParameterizedTests(Class testCase,
			Object[] parameters) {

		Constructor constructor = findConstructor(testCase, parameters);
		if (constructor == null) {
			throw new IllegalArgumentException(
					"The parameters provided don't match a constructor found in ["
							+ testCase.getName() + "]");
		}

		content.add(new ParameterizedTest(testCase, constructor, parameters));

		return this;
	}

	public SuiteBuilder addObservableContractTest(Class testCase,
			IObservableContractDelegate delegate) {

		addParameterizedTests(testCase, new Object[] {delegate});
		return this;
	}

	public TestSuite build() {
		TestSuite suite = new TestSuite();

		for (Iterator it = content.iterator(); it.hasNext();) {
			Object o = it.next();
			if (o instanceof Class) {
				suite.addTestSuite((Class) o);
			} else if (o instanceof ParameterizedTest) {
				ParameterizedTest test = (ParameterizedTest) o;

				TestSuite testClassSuite = new TestSuite();
				testClassSuite.setName(test.testClass.getName());

				TestSuite parameterSuite = new TestSuite();
				parameterSuite.setName(test.parameters[0].getClass().getName());
				testClassSuite.addTest(parameterSuite);

				Method[] methods = test.testClass.getMethods();
				for (int i = 0; i < methods.length; i++) {
					String name = methods[i].getName();
					if (name.startsWith("test")) {
						try {
							parameterSuite.addTest((Test) test.constructor
									.newInstance(toParamArray(name,
											test.parameters)));
						} catch (Exception e) {
							throw new RuntimeException(e);
						}
					}
				}

				if (testClassSuite.countTestCases() > 0)
					suite.addTest(testClassSuite);
			}
		}

		return suite;
	}

	private Object[] toParamArray(String testName, Object[] parameters) {
		Object[] result = new Object[parameters.length + 1];
		result[0] = testName;
		System.arraycopy(parameters, 0, result, 1, parameters.length);
		return result;
	}

	private static Constructor findConstructor(Class clazz, Object[] parameters) {
		Constructor[] constructors = clazz.getConstructors();
		int expectedParametersLength = parameters.length + 1;

		for (int i = 0; i < constructors.length; i++) {
			Constructor constructor = constructors[i];
			Class[] types = constructor.getParameterTypes();

			if (types.length != expectedParametersLength
					|| !String.class.equals(types[0])) {
				continue;
			}

			boolean skip = false;
			for (int j = 1; j < types.length; j++) {
				Class type = types[j];
				if (!type.isInstance(parameters[j - 1])) {
					skip = true;
					break;
				}
			}

			if (!skip) {
				return constructor;
			}
		}

		return null;
	}

		final Constructor constructor;

		final Object[] parameters;

		private Class testClass;

		ParameterizedTest(Class testClass, Constructor constructor,
				Object[] parameterss) {
			this.testClass = testClass;
			this.constructor = constructor;
			this.parameters = parameterss;
		}
	}
}
