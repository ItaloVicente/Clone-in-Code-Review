package org.eclipse.ui.tests.activities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import junit.framework.TestCase;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.expressions.EvaluationResult;
import org.eclipse.core.internal.expressions.TestExpression;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.AbstractSourceProvider;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.IActivityManager;
import org.eclipse.ui.activities.IActivityPatternBinding;
import org.eclipse.ui.activities.IIdentifier;
import org.eclipse.ui.activities.IWorkbenchActivitySupport;
import org.eclipse.ui.activities.WorkbenchActivityHelper;
import org.eclipse.ui.contexts.IContextActivation;
import org.eclipse.ui.contexts.IContextService;
import org.eclipse.ui.services.IEvaluationService;

public class UtilTest extends TestCase {
	
	private Set rememberedSet;
	
	public static final String ID1 = "org.eclipse.ui.tests.util.1";
	public static final String ID2 = "org.eclipse.ui.tests.util.2";
	public static final String ID3 = "org.eclipse.ui.tests.util.3";
	public static final String ID4 = "org.eclipse.ui.tests.util.4";
	public static final String ID5 = "org.eclipse.ui.tests.util.5";
	
	public UtilTest(String name) {
		super(name); 
	}

	public void testGetEnabledCategories1() {
		Set ids = WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID1);
		assertEquals(1, ids.size());
		assertTrue(ids.contains(ID3));
	}

	public void testGetEnabledCategories2() {
		Set ids = WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID2);
		assertEquals(2, ids.size());
		assertTrue(ids.contains(ID1));
		assertTrue(ids.contains(ID3));
	}
		
	public void testGetEnabledCategories3() {
		Set ids = WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID3);
		assertEquals(1, ids.size());
		assertTrue(ids.contains(ID1));
	}
	
	public void testGetEnabledCategories4() {
		Set ids = WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID4);
		assertEquals(0, ids.size());
	}
	
	public void testGetEnabledCategories5() {
		Set ids = WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID5);
		assertEquals(1, ids.size());
		assertTrue(ids.contains(ID4));
	}

	public void testGetEnabledCategories1_A() {
		HashSet set = new HashSet();
		set.add(ID1);
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(set);
		assertEquals(0, WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID1).size());
	}
	
	public void testGetEnabledCategories2_A() {
		HashSet set = new HashSet();
		set.add(ID2);
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(set);
		assertEquals(0, WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID2).size());
	}
	
	public void testGetEnabledCategories3_A() {
		HashSet set = new HashSet();
		set.add(ID1);
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(set);
		assertEquals(0, WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID3).size());
	}	
	
	public void testGetEnabledCategories4_A() {
		HashSet set = new HashSet();
		set.add(ID4);
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(set);
		assertEquals(0, WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID4).size());
	}
	
	public void testGetEnabledCategories5_Aa() {
		HashSet set = new HashSet();
		set.add(ID4);
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(set);
		assertEquals(0, WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID5).size());
	}
	
	public void testGetEnabledCategories5_Ab() {
		HashSet set = new HashSet();
		set.add(ID5);
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(set);
		Set ids = WorkbenchActivityHelper.getEnabledCategories(getActivityManager(), ID5);
		assertEquals(1, ids.size());
		assertTrue(ids.contains(ID4));
	}	
	
	public void testGetDisabledCategories1() {
		enableAll();
		Set ids = WorkbenchActivityHelper.getDisabledCategories(getActivityManager(), ID1);
		assertEquals(1, ids.size());
		assertTrue(ids.contains(ID3));
	}
	
	public void testGetDisabledCategories2() {
		enableAll();
		Set ids = WorkbenchActivityHelper.getDisabledCategories(getActivityManager(), ID2);
		assertEquals(2, ids.size());
		assertTrue(ids.contains(ID1));
		assertTrue(ids.contains(ID3));
	}
	
	public void testGetDisabledCategories3() {
		enableAll();
		Set ids = WorkbenchActivityHelper.getDisabledCategories(getActivityManager(), ID3);
		assertEquals(1, ids.size());
		assertTrue(ids.contains(ID1));
	}
	
	public void testGetDisabledCategories4() {
		enableAll();
		Set ids = WorkbenchActivityHelper.getDisabledCategories(getActivityManager(), ID4);
		assertEquals(0, ids.size());
	}
	
	public void testGetDisabledCategories5() {
		enableAll();
		Set ids = WorkbenchActivityHelper.getDisabledCategories(getActivityManager(), ID5);
		assertEquals(1, ids.size());
		assertTrue(ids.contains(ID4));
	}
	
	public void testCategoryCount1_A() {
		enableAll();
		Set ids = WorkbenchActivityHelper.getEnabledCategoriesForActivity(getActivityManager(), ID1);
		assertEquals(2, ids.size());
		assertTrue(ids.contains(ID1));
		assertTrue(ids.contains(ID3));
	}
	
	public void testCategoryCount2_A() {
		enableAll();
		Set ids = WorkbenchActivityHelper.getEnabledCategoriesForActivity(getActivityManager(), ID2);
		assertEquals(1, ids.size());
		assertTrue(ids.contains(ID2));
	}
	
	public void testCategoryCount4_A() {
		enableAll();
		Set ids = WorkbenchActivityHelper.getEnabledCategoriesForActivity(getActivityManager(), ID4);
		assertEquals(2, ids.size());
		assertTrue(ids.contains(ID4));
		assertTrue(ids.contains(ID5));
	}
	
	public void testCategoryCount5_A() {
		enableAll();
		Set ids = WorkbenchActivityHelper.getEnabledCategoriesForActivity(getActivityManager(), ID5);
		assertEquals(1, ids.size());
		assertTrue(ids.contains(ID5));
	}
	
	public void testPropertyTester1() {
		enableAll();
		EvaluationContext context = new EvaluationContext(null, PlatformUI.getWorkbench());

		IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI
				.getWorkbench().getActivitySupport();
		IActivityManager activityManager = workbenchActivitySupport
				.getActivityManager();
		
		testPropertyTester1(context, activityManager);
		Set set = new HashSet();
		workbenchActivitySupport.setEnabledActivityIds(set);
		
		testPropertyTester1(context, activityManager);
	}

	private void testPropertyTester1(EvaluationContext context,
			IActivityManager activityManager) {
		boolean result = activityManager
				.getActivity(ID1).isEnabled();

		TestExpression test = new TestExpression("org.eclipse.ui",
				"isActivityEnabled", new Object[] { ID1 },
				null);
		
		try {
			assertEquals(result ? EvaluationResult.TRUE: EvaluationResult.FALSE, test.evaluate(context));
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
	
	public void testPropertyTester2() {
		enableAll();
		EvaluationContext context = new EvaluationContext(null, PlatformUI.getWorkbench());

		IWorkbenchActivitySupport workbenchActivitySupport = PlatformUI
				.getWorkbench().getActivitySupport();
		IActivityManager activityManager = workbenchActivitySupport
				.getActivityManager();
		
		testPropertyTester2(context, activityManager);
		Set set = new HashSet();
		workbenchActivitySupport.setEnabledActivityIds(set);
		
		testPropertyTester2(context, activityManager);
	}
	
	
	public static final String EXPRESSION_ACTIVITY_ID = "org.eclipse.ui.tests.filter1.enabled";
	public static final String EXPRESSION_ACTIVITY_ID_2 = "org.eclipse.ui.tests.filter2.enabled";
	public static final String EXPRESSION_ACTIVITY_ID_3 = "org.eclipse.ui.tests.filter3.enabled";
	public static final String EXPRESSION_ACTIVITY_ID_4 = "org.eclipse.ui.tests.filter4.enabled";
	public static final String EXPRESSION_ACTIVITY_ID_5 = "org.eclipse.ui.tests.filter5.enabled";
	public static final String EXPRESSION_ACTIVITY_ID_6 = "org.eclipse.ui.tests.filter6.enabled";
	public static final String EXPRESSION_ACTIVITY_ID_7 = "org.eclipse.ui.tests.filter7.enabled";

	public static final String EXPRESSION_VALUE = "org.eclipse.ui.command.contexts.enablement_test1";

	class TestSourceProvider extends AbstractSourceProvider {
		public static final String VARIABLE = "arbitraryVariable";
		public static final String VALUE = "arbitraryValue";

		private Map sourceState = new HashMap(1);

		public TestSourceProvider() {
			super();
			clearVariable();
		}

		@Override
		public Map getCurrentState() {
			return sourceState;
		}

		@Override
		public String[] getProvidedSourceNames() {
			return new String[] { VARIABLE };
		}

		@Override
		public void dispose() {
		}

		public void fireSourceChanged() {
			fireSourceChanged(0, sourceState);
		}

		public void setVariable() {
			sourceState.put(VARIABLE, VALUE);
		}
		
		public void clearVariable() {
			sourceState.put(VARIABLE, "");
		}
	};
	
	public void testExpressionEnablement() throws Exception {
		IPluginContribution filterExp = new IPluginContribution() {
			@Override
			public String getLocalId() {
				return "filter";
			}
			@Override
			public String getPluginId() {
				return "org";
			}
		};
		IPluginContribution filterExp2 = new IPluginContribution() {
			@Override
			public String getLocalId() {
				return "filter2";
			}
			@Override
			public String getPluginId() {
				return "org";
			}
		};
		IPluginContribution noExp = new IPluginContribution() {
			@Override
			public String getLocalId() {
				return "donotfilter";
			}
			@Override
			public String getPluginId() {
				return "org";
			}
		};
		assertTrue(WorkbenchActivityHelper.filterItem(filterExp));
		assertTrue(WorkbenchActivityHelper.filterItem(noExp));
		assertTrue(WorkbenchActivityHelper.restrictUseOf(filterExp));
		assertFalse(WorkbenchActivityHelper.restrictUseOf(noExp));
		
		IWorkbenchActivitySupport support = PlatformUI.getWorkbench()
				.getActivitySupport();
		Set enabledActivityIds = support.getActivityManager()
				.getEnabledActivityIds();
		assertTrue(enabledActivityIds.contains(EXPRESSION_ACTIVITY_ID_3));
		
		assertFalse(enabledActivityIds.contains(EXPRESSION_ACTIVITY_ID_4));
		assertFalse(enabledActivityIds.contains(EXPRESSION_ACTIVITY_ID_5));
		enabledActivityIds = new HashSet(enabledActivityIds);
		enabledActivityIds.add(EXPRESSION_ACTIVITY_ID_5);
		support.setEnabledActivityIds(enabledActivityIds);
		enabledActivityIds = support.getActivityManager()
				.getEnabledActivityIds();
		assertFalse(enabledActivityIds.contains(EXPRESSION_ACTIVITY_ID_4));
		assertTrue(enabledActivityIds.contains(EXPRESSION_ACTIVITY_ID_5));
		
		assertFalse(enabledActivityIds.contains(EXPRESSION_ACTIVITY_ID_6));
		assertTrue(enabledActivityIds.contains(EXPRESSION_ACTIVITY_ID_7));
		
		
		
		IContextService localService = PlatformUI
				.getWorkbench().getService(IContextService.class);
		IContextActivation activation = localService.activateContext(EXPRESSION_VALUE);
		try {
		assertFalse(WorkbenchActivityHelper.restrictUseOf(filterExp));

		localService.deactivateContext(activation);
		assertTrue(WorkbenchActivityHelper.restrictUseOf(filterExp));		

		TestSourceProvider testSourceProvider = new TestSourceProvider();
		IEvaluationService evalService = PlatformUI
				.getWorkbench().getService(IEvaluationService.class);
		evalService.addSourceProvider(testSourceProvider);
		testSourceProvider.fireSourceChanged();

		assertTrue(WorkbenchActivityHelper.restrictUseOf(filterExp2));

		testSourceProvider.setVariable();
		testSourceProvider.fireSourceChanged();
		assertFalse(WorkbenchActivityHelper.restrictUseOf(filterExp2));
		
		testSourceProvider.clearVariable();
		testSourceProvider.fireSourceChanged();
		
		Set set = new HashSet(support.getActivityManager().getEnabledActivityIds());
		set.add(EXPRESSION_ACTIVITY_ID_2);
		support.setEnabledActivityIds(set);
		
		testSourceProvider.setVariable();
		testSourceProvider.fireSourceChanged();
		assertFalse(WorkbenchActivityHelper.restrictUseOf(filterExp2));

		evalService.removeSourceProvider(testSourceProvider);
		}
		finally {
			localService.deactivateContext(activation);
		}
	}
	
	private void testPropertyTester2(EvaluationContext context,
			IActivityManager activityManager) {
		boolean result = WorkbenchActivityHelper.isEnabled(activityManager, ID1);


		TestExpression test = new TestExpression("org.eclipse.ui",
				"isCategoryEnabled", new Object[] { ID1 },
				null);
		
		try {
			assertEquals(result ? EvaluationResult.TRUE: EvaluationResult.FALSE, test.evaluate(context));
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}
	
	private void enableAll() {
		HashSet set = new HashSet();
		set.add(ID1);
		set.add(ID2);
		set.add(ID4);
		set.add(ID5);
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(
				set);
	}

    private IActivityManager getActivityManager() {
        return  PlatformUI.getWorkbench()
        .getActivitySupport().getActivityManager();
    }
    
    public void testNonRegExpressionPattern() {    	
    	final String ACTIVITY_NON_REG_EXP = "org.eclipse.activityNonRegExp";
    	
    	IActivityManager manager = getActivityManager();    	
    	IActivity activity = manager.getActivity(ACTIVITY_NON_REG_EXP);
    	Set bindings = activity.getActivityPatternBindings();
    	assertTrue(bindings.size() == 1);
    	IActivityPatternBinding binding = 
    		(IActivityPatternBinding)bindings.iterator().next();
    	assertTrue(binding.isEqualityPattern());
    	
    	final String IDENTIFIER = "org.eclipse.ui.tests.activity{No{Reg(Exp[^d]";
    	IIdentifier identifier = manager.getIdentifier(IDENTIFIER);
    	Set boundActivities = identifier.getActivityIds();
    	assertTrue(boundActivities.size() == 1);
    	String id = boundActivities.iterator().next().toString();
    	assertTrue(id.equals(ACTIVITY_NON_REG_EXP));
    	
    	Pattern pattern = binding.getPattern();    	
    	assertTrue(pattern.pattern().equals(
				Pattern.compile("\\Q" + IDENTIFIER + "\\E").pattern()));
    }    
    
    public void testSetEnabledExpressionActivity() {
    	try {
    		TestSourceProvider testSourceProvider = new TestSourceProvider();
    		IEvaluationService evalService = PlatformUI
    				.getWorkbench().getService(IEvaluationService.class);
    		evalService.addSourceProvider(testSourceProvider);
    		testSourceProvider.fireSourceChanged();
    		
    		
    		IWorkbenchActivitySupport support = PlatformUI.getWorkbench()
    			.getActivitySupport();		
    		support.setEnabledActivityIds(new HashSet());
    		Set set = new HashSet(support.getActivityManager().getEnabledActivityIds());
    		Set previousSet = new HashSet(support.getActivityManager().getEnabledActivityIds());
    		set.add(EXPRESSION_ACTIVITY_ID_2);
    		support.setEnabledActivityIds(set);
    		assertEquals(previousSet, support.getActivityManager().getEnabledActivityIds());
    		
    		testSourceProvider.setVariable();
    		testSourceProvider.fireSourceChanged();
    		
    		set = new HashSet(support.getActivityManager().getEnabledActivityIds());
    		assertFalse(set.equals(previousSet));
    		
    		set.remove(EXPRESSION_ACTIVITY_ID_2);
    		support.setEnabledActivityIds(set);
    		
    		assertFalse(support.getActivityManager().getEnabledActivityIds().equals(previousSet));

    		evalService.removeSourceProvider(testSourceProvider);
    	}
    	finally {
    		
    	}
    }
    
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		rememberedSet = getActivityManager().getEnabledActivityIds();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		PlatformUI.getWorkbench().getActivitySupport().setEnabledActivityIds(
				rememberedSet);
	}
}
