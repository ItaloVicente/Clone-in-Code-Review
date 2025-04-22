
package org.eclipse.ui.tests.performance;

import org.eclipse.ui.activities.IActivityManager;

public class GenerateIdentifiersTest extends BasicPerformanceTest {

    private int count;

    public GenerateIdentifiersTest(int numberOfIdentifiers) {
        super("Generate " + numberOfIdentifiers + " identifiers");
        this.count = numberOfIdentifiers;
    }
    
    protected void runTest() throws Throwable {       
        final IActivityManager activityManager = fWorkbench.getActivitySupport().getActivityManager();
        
        exercise(new TestRunnable() {
            public void run() throws Exception {
                final String [] ids = new String[count];
                for (int i = 0; i < ids.length; i++) {
                    long timestamp = System.currentTimeMillis();
                    ids[i] = "org.eclipse.jdt.ui/" + i + timestamp;
                }
                
                startMeasuring();
                for(int i = 0; i < ids.length; i++) {
                    activityManager.getIdentifier(ids[i]);
                }                
                stopMeasuring();
            } 
        });
        commitMeasurements();
        assertPerformance();
    }
}
