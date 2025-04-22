package org.eclipse.e4.ui.tests.css.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.eclipse.e4.ui.css.core.dom.IElementProvider;
import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.impl.engine.CSSEngineImpl;
import org.eclipse.e4.ui.tests.css.core.util.TestElement;
import org.junit.Test;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;
import org.w3c.dom.Element;

public class CSSEngineTest {

	private static class TestCSSEngine extends CSSEngineImpl {
		@Override
		public void reapply() {
		}
	}

	@Test
	public void testSelectorMatch() throws Exception {
		TestCSSEngine engine = new TestCSSEngine();
		SelectorList list = engine.parseSelectors("Date");
		engine.setElementProvider(new IElementProvider() {
			@Override
			public Element getElement(Object element, CSSEngine engine) {
				return new TestElement(element.getClass().getSimpleName(),
						engine);
			}
		});
		assertFalse(engine.matches(list.item(0), new Object(), null));
		assertTrue(engine.matches(list.item(0), new Date(), null));
	}

	@Test
	public void testSelectorMatchOneOf() throws Exception {
		TestCSSEngine engine = new TestCSSEngine();
		engine.setElementProvider(new IElementProvider() {
			@Override
			public Element getElement(Object element, CSSEngine engine) {
				Element e = new TestElement("E", engine);
				e.setAttribute("a", element.toString());
				return e;
			}
		});
		Selector selector = engine.parseSelectors("E[a~='B']").item(0);
		assertTrue(engine.matches(selector, "B AB", null));
		assertTrue(engine.matches(selector, "BC B", null));
		assertFalse(engine.matches(selector, "ABC", null));
		assertTrue(engine.matches(selector, "B", null));
	}
}
