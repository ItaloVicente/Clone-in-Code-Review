package org.eclipse.ui.tests.harness.util;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class CallHistory {
    private ArrayList methodList;

    private Class classType;

    public CallHistory(Object target) {
        methodList = new ArrayList();
        classType = target.getClass();
    }

    private void testMethodName(String methodName) {
        Method[] methods = classType.getMethods();
        for (int i = 0; i < methods.length; i++)
            if (methods[i].getName().equals(methodName))
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
        for (int nX = 0; nX < methodList.size(); nX++) {
            String methodName = (String) methodList.get(nX);
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
        for (int i = 0; i < methodNames.length; i++) {
            testMethodName(methodNames[i]);
            if (!methodList.contains(methodNames[i]))
                return false;
        }
        return true;
    }

    public boolean isEmpty() {
        return methodList.isEmpty();
    }

    public void printToConsole() {
        for (int i = 0; i < methodList.size(); i++)
            System.out.println(methodList.get(i));
    }
}
