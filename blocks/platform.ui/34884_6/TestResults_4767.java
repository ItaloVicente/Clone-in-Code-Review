package org.eclipse.ui.tests.autotests;

import junit.framework.Assert;

import org.eclipse.ui.IMemento;

public class TestResultFilter {
    private static final String ATT_RESULT = "result";
    private static final String ATT_EXCEPTION = "exception";
    private String result;
    private String expectedException;
    
    public TestResultFilter(IMemento toLoad) {
        this.result = toLoad.getString(ATT_RESULT);
        this.expectedException = toLoad.getString(ATT_EXCEPTION);
    }
    
    public TestResultFilter(TestResult toAccept) {
        if (toAccept.getException() != null) {
            result = null;
            expectedException = toAccept.getException().toString();
        } else {
            result = toAccept.getReturnValue();
            expectedException = null;
        }
    }
    
    public void saveState(IMemento toSave) {
        if (result != null) {
            toSave.putString(ATT_RESULT, result);
        }
        
        if (expectedException != null) {
            toSave.putString(ATT_EXCEPTION, expectedException);
        }
    }
    
    public void assertResult(TestResult actual) throws Throwable {
        if (result != null) {
            if (actual.getException() != null) {
                throw actual.getException();
            }
            
            Assert.assertEquals(result, actual.getReturnValue());
        } else {       
            if (actual.getException() == null) {
                Assert.assertTrue("Test should have thrown exception " + expectedException + " but returned result " 
                    + actual.getReturnValue(), false);
            }
            Assert.assertEquals("Test threw wrong type of exception", actual.getException().toString(), expectedException);
        }
    }
}
