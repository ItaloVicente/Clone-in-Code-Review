package org.eclipse.ui.tests.autotests;


public class TestResult {
    private String result;
    private Throwable thrownException;
    
    public TestResult(String expectedResult) {
        this.result = expectedResult;
    }
    
    public TestResult(Throwable t) {
        this.result = null;
        this.thrownException = t;
    }
    
    public String getReturnValue() {
        return result;
    }
    
    public Throwable getException() {
        return thrownException;
    }
    
}
