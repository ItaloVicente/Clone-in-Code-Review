
package org.eclipse.jface.tests.databinding.swt;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.beans.BeanProperties;
import org.eclipse.core.tests.databinding.observable.ThreadRealm;
import org.eclipse.core.tests.internal.databinding.beans.Bean;
import org.eclipse.jface.databinding.conformance.util.RealmTester;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.jface.tests.databinding.AbstractSWTTestCase;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

public class WidgetObservableThreadTest extends AbstractSWTTestCase {
	protected ThreadRealm threadRealm;
	private DataBindingContext ctx;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		threadRealm = new ThreadRealm();
		new Thread() {
			@Override
			public void run() {
				RealmTester.setDefault(threadRealm);
				threadRealm.init(Thread.currentThread());
				threadRealm.block();
			}
		}.start();

        threadRealm.waitUntilBlocking();
	}

	@Override
	protected void tearDown() throws Exception {
		if (ctx != null) {
			threadRealm.exec(new Runnable() {
				@Override
				public void run() {
					ctx.dispose();
					ctx = null;
				}
			});
			threadRealm.processQueue();
		}

		threadRealm.unblock();
		threadRealm = null;

		super.tearDown();
	}

	public void testBindWidgetObservableFromNonDisplayThread() {
		final Text text = new Text(getShell(), SWT.BORDER);
		final Bean bean = new Bean("oldValue");

		assertEquals("oldValue", bean.getValue());
		assertEquals("", text.getText());

		threadRealm.exec(new Runnable() {
			@Override
			public void run() {
				ctx = new DataBindingContext();
				ctx.bindValue(WidgetProperties.text(SWT.Modify).observe(text),
						BeanProperties.value("value")
								.observe(threadRealm, bean));
			}
		});
		threadRealm.processQueue();

		assertEquals("oldValue", bean.getValue());
		assertEquals("", text.getText());

		processDisplayQueue();
		threadRealm.processQueue();

		assertEquals("oldValue", bean.getValue());
		assertEquals("oldValue", text.getText());

		text.setText("newValue");
		threadRealm.processQueue();

		assertEquals("newValue", bean.getValue());
		assertEquals("newValue", text.getText());

		threadRealm.exec(new Runnable() {
			@Override
			public void run() {
				bean.setValue("newerValue");
			}
		});
		threadRealm.processQueue();

		assertEquals("newerValue", bean.getValue());
		assertEquals("newValue", text.getText());

		processDisplayQueue();

		assertEquals("newerValue", bean.getValue());
		assertEquals("newerValue", text.getText());
	}

	private void processDisplayQueue() {
		while (Display.getCurrent().readAndDispatch()) {
		}
	}
}
