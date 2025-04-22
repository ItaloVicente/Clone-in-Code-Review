package org.eclipse.e4.ui.tests.css.swt;

import java.io.IOException;
import java.io.StringReader;

import junit.framework.TestCase;

import org.eclipse.e4.ui.css.core.engine.CSSEngine;
import org.eclipse.e4.ui.css.core.engine.CSSErrorHandler;
import org.eclipse.e4.ui.css.swt.engine.CSSSWTEngineImpl;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

public class CSSSWTTestCase extends TestCase {
	
	public CSSEngine createEngine(String styleSheet, Display display) {
		CSSEngine engine = new CSSSWTEngineImpl(display);
		
		engine.setErrorHandler(new CSSErrorHandler() {
			public void error(Exception e) {
				fail(e.getMessage());
			}
		});
		
		try {
			engine.parseStyleSheet(new StringReader(styleSheet));
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return engine;
	
	}
	
	public void clearAndApply(CSSEngine engine, Widget widget, String styleSheet) {

		engine.reset();

		try {
			engine.parseStyleSheet(new StringReader(styleSheet));
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		engine.applyStyles(widget, true, true);
	}
	
	static public void assertEquals(int[] expected, int[] actual) {
		assertEquals(expected.length, actual.length);
		for (int i = 0; i < actual.length; i++) {
		    assertEquals(expected[i], actual[i]);			
		}
	}

    @Override
    protected void tearDown() throws Exception {
        Display display = Display.getDefault();
        if (!display.isDisposed()) {
            for (Shell shell : display.getShells()) {
                shell.dispose();
            }
        }
        super.tearDown();
    }

}
