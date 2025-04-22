package org.eclipse.jface.databinding.conformance.util;

import java.util.ArrayList;
import java.util.List;

public class ConformanceSuite {

	private final List<Object[]> tests = new ArrayList<>();

	public void addTest(Class<?> testClass, Object delegate) {
		tests.add(new Object[] { testClass, delegate });
	}

	public Iterable<Object[]> getDataForParameterizedRunner() {
		return tests;
	}
}
