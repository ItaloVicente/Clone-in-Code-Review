package org.eclipse.ui.tests.autotests;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.ui.IMemento;

public class TestResults {
    private Map results = new HashMap();
    private final static String ATT_NAME = "name";
    private final static String ATT_TEST = "test";

    public TestResults() {
    }

    public TestResults(IMemento toLoad) {
        IMemento[] tests = toLoad.getChildren(ATT_TEST);

        for (IMemento memento : tests) {
            String name = memento.getString(ATT_NAME);
            if (name == null) {
                continue;
            }

            results.put(name, new TestResultFilter(memento));
        }
    }

    public String[] getTestNames() {
        Collection ids = results.keySet();

        return (String[])ids.toArray(new String[ids.size()]);
    }

    public TestResultFilter get(String testName) {
        return (TestResultFilter) results.get(testName);
    }

    public void put(String testName, TestResultFilter filter) {
        results.put(testName, filter);
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    public void saveState(IMemento memento) {
        for (Iterator iter = results.keySet().iterator(); iter.hasNext();) {
            String testName = (String) iter.next();

            TestResultFilter next = get(testName);

            IMemento child = memento.createChild(ATT_TEST);
            child.putString(ATT_NAME, testName);
            next.saveState(child);
        }
    }
}
