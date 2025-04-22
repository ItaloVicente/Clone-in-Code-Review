package org.eclipse.ui.tests.dynamicplugins;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.activities.IActivity;
import org.eclipse.ui.activities.ICategory;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;
import org.eclipse.ui.tests.harness.util.ImageTests;

public class ActivitySupportTests extends DynamicTestCase {

	public ActivitySupportTests(String testName) {
		super(testName);
	}

	@Override
	protected String getExtensionId() {
		return "newActivitySupport1.testNewActivitySupportAddition";
	}

	@Override
	protected String getExtensionPoint() {
		return IWorkbenchRegistryConstants.PL_ACTIVITYSUPPORT;
	}

	@Override
	protected String getInstallLocation() {
		return "data/org.eclipse.newActivitySupport1";
	}

	public void testActivityImages() {
		IActivity baselineActivity = getWorkbench().getActivitySupport()
				.getActivityManager().getActivity("someBogusActivityId");
		assertNotNull(baselineActivity);
		assertFalse(baselineActivity.isDefined());

		IActivity activityWithIcon = getWorkbench().getActivitySupport()
				.getActivityManager().getActivity("org.eclipse.activity2");
		assertNotNull(activityWithIcon);
		assertTrue(activityWithIcon.isDefined());

		ImageDescriptor baselineDescriptor = getWorkbench()
				.getActivitySupport().getImageDescriptor(baselineActivity);
		ImageDescriptor customDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(activityWithIcon);

		Image baselineImage = null, customImage = null;
		try {
			baselineImage = baselineDescriptor.createImage();
			assertNotNull(baselineImage);
			customImage = customDescriptor.createImage();
			assertNotNull(customImage);

			ImageTests.assertEquals(baselineImage, customImage);
		} finally {
			if (baselineImage != null) {
				baselineImage.dispose();
			}
			if (customImage != null) {
				customImage.dispose();
			}
		}

		getBundle();

		baselineDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(baselineActivity);
		customDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(activityWithIcon);

		try {
			baselineImage = baselineDescriptor.createImage();
			assertNotNull(baselineImage);
			customImage = customDescriptor.createImage();
			assertNotNull(customImage);

			ImageTests.assertNotEquals(baselineImage, customImage);
		} finally {
			if (baselineImage != null) {
				baselineImage.dispose();
			}
			if (customImage != null) {
				customImage.dispose();
			}
		}

		removeBundle();

		baselineDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(baselineActivity);
		customDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(activityWithIcon);

		try {
			baselineImage = baselineDescriptor.createImage();
			assertNotNull(baselineImage);
			customImage = customDescriptor.createImage();
			assertNotNull(customImage);

			ImageTests.assertEquals(baselineImage, customImage);
		} finally {
			if (baselineImage != null) {
				baselineImage.dispose();
			}
			if (customImage != null) {
				customImage.dispose();
			}
		}

	}


	public void testCategoryImages() {
		ICategory baselineCategory = getWorkbench().getActivitySupport()
				.getActivityManager().getCategory("someBogusCategoryId");
		assertNotNull(baselineCategory);
		assertFalse(baselineCategory.isDefined());

		ICategory categoryWithIcon = getWorkbench().getActivitySupport()
				.getActivityManager().getCategory("org.eclipse.category2");
		assertNotNull(categoryWithIcon);
		assertTrue(categoryWithIcon.isDefined());

		ImageDescriptor baselineDescriptor = getWorkbench()
				.getActivitySupport().getImageDescriptor(baselineCategory);
		ImageDescriptor customDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(categoryWithIcon);

		Image baselineImage = null, customImage = null;
		try {
			baselineImage = baselineDescriptor.createImage();
			assertNotNull(baselineImage);
			customImage = customDescriptor.createImage();
			assertNotNull(customImage);

			ImageTests.assertEquals(baselineImage, customImage);
		} finally {
			if (baselineImage != null) {
				baselineImage.dispose();
			}
			if (customImage != null) {
				customImage.dispose();
			}
		}

		getBundle();

		baselineDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(baselineCategory);
		customDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(categoryWithIcon);

		try {
			baselineImage = baselineDescriptor.createImage();
			assertNotNull(baselineImage);
			customImage = customDescriptor.createImage();
			assertNotNull(customImage);

			ImageTests.assertNotEquals(baselineImage, customImage);
		} finally {
			if (baselineImage != null) {
				baselineImage.dispose();
			}
			if (customImage != null) {
				customImage.dispose();
			}
		}

		removeBundle();

		baselineDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(baselineCategory);
		customDescriptor = getWorkbench().getActivitySupport()
				.getImageDescriptor(categoryWithIcon);

		try {
			baselineImage = baselineDescriptor.createImage();
			assertNotNull(baselineImage);
			customImage = customDescriptor.createImage();
			assertNotNull(customImage);

			ImageTests.assertEquals(baselineImage, customImage);
		} finally {
			if (baselineImage != null) {
				baselineImage.dispose();
			}
			if (customImage != null) {
				customImage.dispose();
			}
		}

	}
}
