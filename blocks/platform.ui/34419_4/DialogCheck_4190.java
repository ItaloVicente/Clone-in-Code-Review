package org.eclipse.ui.tests.harness.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CallHistory {
	private List<String> methodList;

    private Class<? extends Object> classType;

    public CallHistory(Object target) {
        methodList = new ArrayList<String>();
        classType = target.getClass();
    }

    private void testMethodName(String methodName) {
        Method[] methods = classType.getMethods();
		for (Method method : methods)
			if (method.getName().equals(methodName))
                return;
        throw new IllegalArgumentException("Target class ("
                + classType.getName() + ") does not contain method: "
                + methodName);
    }

    public void add(String methodName) {
        testMethodName(methodName);
        methodList.add(methodName);
    }

    public void clear() {
        methodList.clear();
    }

    public boolean verifyOrder(String[] testNames)
            throws IllegalArgumentException {
        int testIndex = 0;
        int testLength = testNames.length;
        if (testLength == 0)
            return true;
		for (String methodName : methodList) {
            String testName = testNames[testIndex];
            testMethodName(testName);
            if (testName.equals(methodName))
                ++testIndex;
            if (testIndex >= testLength)
                return true;
        }
        return false;
    }

    public boolean contains(String methodName) {
        testMethodName(methodName);
        return methodList.contains(methodName);
    }

    public boolean contains(String[] methodNames) {
		for (String methodName : methodNames) {
			testMethodName(methodName);
			if (!methodList.contains(methodNames))
                return false;
        }
        return true;
    }

    public boolean isEmpty() {
        return methodList.isEmpty();
    }

    public void printToConsole() {
		for (String methodName : methodList)
			System.out.println(methodName);
    }
}
