
package org.eclipse.ui.tests.navigator;

import org.eclipse.ui.internal.navigator.VisibilityAssistant;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentDescriptor;
import org.eclipse.ui.internal.navigator.extensions.NavigatorContentDescriptorManager.EvaluationCache;
import org.eclipse.ui.navigator.INavigatorActivationService;
import org.eclipse.ui.navigator.INavigatorViewerDescriptor;
import org.eclipse.ui.tests.navigator.util.TestNavigatorActivationService;
import org.eclipse.ui.tests.navigator.util.TestNavigatorViewerDescriptor;
import org.junit.Assert;

public class NavigatorContentDescriptorManagerEvaluationCacheTest extends NavigatorTestBase {
	EvaluationCacheExposed cache;

	public NavigatorContentDescriptorManagerEvaluationCacheTest() {
		_navigatorInstanceId = TEST_VIEWER_PROGRAMMATIC;
	}

	private static final class EvaluationCacheExposed extends EvaluationCache {

		EvaluationCacheExposed(VisibilityAssistant anAssistant) {
			super(anAssistant);
		}

		public NavigatorContentDescriptor[] getDescriptorsPublic(Object anElement, boolean toComputeOverrides) {
			return getDescriptors(anElement, toComputeOverrides);
		}

		public void setDescriptorsPublic(Object anElement, NavigatorContentDescriptor[] theDescriptors, boolean toComputeOverrides) {
			setDescriptors(anElement, theDescriptors, toComputeOverrides);
		}
	}

	@Override
	protected void setUp() {
		super.setUp();
		INavigatorViewerDescriptor mockViewerDescript = new TestNavigatorViewerDescriptor();
		INavigatorActivationService mockActivationService = new TestNavigatorActivationService();
		VisibilityAssistant mockAssistant = new VisibilityAssistant(mockViewerDescript, mockActivationService);

		cache = new EvaluationCacheExposed(mockAssistant);
	}

	private void doSimpleAddGet(boolean toComputeOverrides) {
		Object key = new Object();
		NavigatorContentDescriptor[] value = new NavigatorContentDescriptor[0];
		cache.setDescriptorsPublic(key, value, toComputeOverrides);
		Assert.assertSame(value, cache.getDescriptorsPublic(key, toComputeOverrides));
		Assert.assertNull(cache.getDescriptorsPublic(key, !toComputeOverrides));
	}

	public void testSimpleAddGetNotOverrides() {
		doSimpleAddGet(false);
	}

	public void testSimpleAddGetOverrides() {
		doSimpleAddGet(true);
	}

	private void doNotSameInstEqual(boolean toComputeOverrides) {
		java.util.List<String> key = new java.util.ArrayList<String>(2);
		key.add("Hi");
		key.add("There");
		NavigatorContentDescriptor[] value = new NavigatorContentDescriptor[0];
		cache.setDescriptorsPublic(key, value, toComputeOverrides);
		java.util.List key2 = new java.util.ArrayList<String>(key);
		Assert.assertSame(value, cache.getDescriptorsPublic(key2, toComputeOverrides));
		Assert.assertNull(cache.getDescriptorsPublic(key, !toComputeOverrides));
		Assert.assertNull(cache.getDescriptorsPublic(key2, !toComputeOverrides));
	}

	public void testNotSameInstEqualNotOverrides() {
		doNotSameInstEqual(false);
	}

	public void testNotSameInstEqualOverrides() {
		doNotSameInstEqual(true);
	}

	private void doTestReplace(boolean toComputeOverrides) {
		Object key = new Object();
		NavigatorContentDescriptor[] value1 = new NavigatorContentDescriptor[0];
		cache.setDescriptorsPublic(key, value1, toComputeOverrides);
		Assert.assertSame(value1, cache.getDescriptorsPublic(key, toComputeOverrides));
		NavigatorContentDescriptor[] value2 = new NavigatorContentDescriptor[0];
		cache.setDescriptorsPublic(key, value2, toComputeOverrides);
		Assert.assertSame(value2, cache.getDescriptorsPublic(key, toComputeOverrides));
	}

	public void testReplaceNotOverrides() {
		doTestReplace(false);
	}

	public void testReplaceOverrides() {
		doTestReplace(true);
	}

	public void testOnVisibilityOrActivationChangeClearsCaches() {
		Object key = new Object();
		NavigatorContentDescriptor[] value1 = new NavigatorContentDescriptor[0];
		cache.setDescriptorsPublic(key, value1, false);
		Assert.assertSame(value1, cache.getDescriptorsPublic(key, false));
		NavigatorContentDescriptor[] value2 = new NavigatorContentDescriptor[0];
		cache.setDescriptorsPublic(key, value2, true);
		Assert.assertSame(value2, cache.getDescriptorsPublic(key, true));
		cache.onVisibilityOrActivationChange();
		Assert.assertNull(cache.getDescriptorsPublic(key, false));
		Assert.assertNull(cache.getDescriptorsPublic(key, true));
	}

}
