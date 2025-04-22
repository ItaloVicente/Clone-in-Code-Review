package org.eclipse.ui.tests.performance;

import junit.framework.TestCase;

import org.eclipse.test.performance.Dimension;
import org.eclipse.test.performance.Performance;
import org.eclipse.test.performance.PerformanceMeter;

final class PerformanceTester {

	protected PerformanceMeter fPerformanceMeter;

	public PerformanceTester(TestCase testCase) {
		Performance performance= Performance.getDefault();
		fPerformanceMeter= performance.createPerformanceMeter(performance.getDefaultScenarioId(testCase));
	}

	public void assertPerformance() {
		Performance.getDefault().assertPerformance(fPerformanceMeter);
	}

	public void assertPerformanceInRelativeBand(Dimension dim, int lowerPercentage, int upperPercentage) {
		Performance.getDefault().assertPerformanceInRelativeBand(fPerformanceMeter, dim, lowerPercentage, upperPercentage);
	}

	public void commitMeasurements() {
		fPerformanceMeter.commit();
	}

	public void dispose() {
		fPerformanceMeter.dispose();
	}

	public void startMeasuring() {
		fPerformanceMeter.start();
	}

	public void stopMeasuring() {
		fPerformanceMeter.stop();
	}

	public void tagAsGlobalSummary(String shortName, Dimension dimension) {
		Performance.getDefault().tagAsGlobalSummary(fPerformanceMeter, shortName, new Dimension[] { dimension });
	}

	public void tagAsGlobalSummary(String shortName, Dimension[] dimensions) {
		Performance.getDefault().tagAsGlobalSummary(fPerformanceMeter, shortName, dimensions);
	}

	public void tagAsSummary(String shortName, Dimension dimension) {
		Performance.getDefault().tagAsSummary(fPerformanceMeter, shortName, new Dimension[] { dimension });
	}

	public void tagAsSummary(String shortName, Dimension[] dimensions) {
		Performance.getDefault().tagAsSummary(fPerformanceMeter, shortName, dimensions);
	}

	public void setDegradationComment(String string) {
		Performance.getDefault().setComment(
				fPerformanceMeter, 
				Performance.EXPLAINS_DEGRADATION_COMMENT, 
				string);
		
	}
}
