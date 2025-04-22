package org.eclipse.ui.tests.autotests;

public interface AutoTest {
    public abstract String getName();
    public abstract String performTest() throws Throwable;
}
