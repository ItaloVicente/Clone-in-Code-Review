
package org.eclipse.ui.tests.navigator;

import static org.junit.Assert.assertEquals;

import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.tests.navigator.m12.model.M1Project;
import org.junit.Test;

public class FirstClassM1Tests extends NavigatorTestBase {
	public FirstClassM1Tests() {
		_navigatorInstanceId = TEST_CONTENT_M12_VIEW;
	}

	@Test
	public void testM1ProjectHasChildren() throws Exception {
		String[] EXTENSIONS = new String[] { COMMON_NAVIGATOR_RESOURCE_EXT,
				TEST_CONTENT_M12_M1_CONTENT_FIRST_CLASS,
				TEST_CONTENT_M12_M2_CONTENT };
		_contentService.bindExtensions(EXTENSIONS, false);
		_contentService.getActivationService().activateExtensions(EXTENSIONS,
				true);

		TreeItem[] rootItems = _viewer.getTree().getItems();
		TreeItem p1Item = rootItems[_p1Ind];

		assertEquals("P1 tree item should be an M1Project", M1Project.class,
				p1Item.getData().getClass());

		_expand(rootItems);
		TreeItem[] p1Children = p1Item.getItems();

		assertEquals("Project should have 3 children", 3, p1Children.length);

	}

}
