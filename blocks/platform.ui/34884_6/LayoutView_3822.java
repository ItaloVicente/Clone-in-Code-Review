package org.eclipse.e4.ui.tests.workbench;

import javax.inject.Inject;
import javax.inject.Singleton;
import junit.framework.TestCase;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.IInjector;
import org.eclipse.e4.core.di.InjectorFactory;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.di.extensions.EventTopic;
import org.eclipse.e4.core.di.extensions.EventUtils;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.tests.Activator;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.widgets.Display;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.service.event.EventAdmin;

public class InjectionEventTest extends TestCase {

	static protected boolean testFailed = false;

	static class InjectTarget {
		public int counter1 = 0;
		public int counter2 = 0;
		public int counter3 = 0;

		public String string1;
		public String string2;
		public String string3;

		public boolean valid = true;

		public MyBinding myBinding;

		public void resetCounters() {
			counter1 = counter2 = counter3 = 0;
		}

		@Inject
		@Optional
		public void receivedEvent1(@EventTopic("e4/test/event1") String string1) {
			if (!valid)
				testFailed = true;
			counter1++;
			this.string1 = string1;
		}

		@Inject
		@Optional
		public void receivedEvent2(
				@UIEventTopic("e4/test/event2") String string2) {
			if (!valid)
				testFailed = true;
			counter2++;
			this.string2 = string2;
		}

		@Inject
		public void receivedOptionalEvent(MyBinding myBinding,
				@Optional @EventTopic("e4/test/event3") String string3) {
			if (!valid)
				testFailed = true;
			counter3++;
			this.myBinding = myBinding;
			this.string3 = string3;
		}
	}

	static class InjectStarEvent {
		public int counter1 = 0;
		public String data;

		@Inject
		@Optional
		public void receivedEvent1(@UIEventTopic("e4/test/*") String data) {
			counter1++;
			this.data = data;
		}
	}

	static public class EventAdminHelper {
		@Inject
		public EventAdmin eventAdmin;

		public void sendEvent(String topic, Object data) {
			EventUtils.send(eventAdmin, topic, data);
		}
	}

	@Singleton
	static class MyBinding {
	}

	private EventAdminHelper helper;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ensureEventAdminStarted();
		BundleContext bundleContext = Activator.getDefault().getBundle()
				.getBundleContext();
		IEclipseContext localContext = EclipseContextFactory
				.getServiceContext(bundleContext);
		helper = ContextInjectionFactory.make(EventAdminHelper.class,
				localContext);
	}

	public void testEventInjection() {
		IInjector injector = InjectorFactory.getDefault();
		injector.addBinding(MyBinding.class);

		IEclipseContext context = EclipseContextFactory.create();
		final Display d = Display.getDefault();

		context.set(Realm.class, SWTObservables.getRealm(d));
		context.set(UISynchronize.class, new UISynchronize() {

			public void syncExec(Runnable runnable) {
				d.syncExec(runnable);
			}

			public void asyncExec(Runnable runnable) {
				d.asyncExec(runnable);
			}
		});
		ContextInjectionFactory.setDefault(context);
		InjectTarget target = ContextInjectionFactory.make(InjectTarget.class,
				context);

		assertEquals(0, target.counter1);
		assertNull(target.string1);
		assertEquals(0, target.counter2);
		assertNull(target.string2);
		assertEquals(1, target.counter3);
		assertNull(target.string3);
		assertNotNull(target.myBinding);

		helper.sendEvent("e4/test/event1", "event1data");

		assertEquals(1, target.counter1);
		assertEquals("event1data", target.string1);
		assertEquals(0, target.counter2);
		assertNull(target.string2);
		assertEquals(1, target.counter3);
		assertNull(target.string3);
		assertNotNull(target.myBinding);

		helper.sendEvent("e4/test/event2", "event2data");

		assertEquals(1, target.counter1);
		assertEquals("event1data", target.string1);
		assertEquals(1, target.counter2);
		assertEquals("event2data", target.string2);
		assertEquals(1, target.counter3);
		assertNull(target.string3);
		assertNotNull(target.myBinding);

		helper.sendEvent("e4/test/event3", "event3data");

		assertEquals(1, target.counter1);
		assertEquals("event1data", target.string1);
		assertEquals(1, target.counter2);
		assertEquals("event2data", target.string2);
		assertEquals(2, target.counter3);
		assertEquals("event3data", target.string3);
		assertNotNull(target.myBinding);

		helper.sendEvent("e4/test/event1", "abc");

		assertEquals(2, target.counter1);
		assertEquals("abc", target.string1);
		assertEquals(1, target.counter2);
		assertEquals("event2data", target.string2);
		assertEquals(2, target.counter3);
		assertEquals("event3data", target.string3);
		assertNotNull(target.myBinding);
	}

	public void testEventInjectionUnsubscribe() {
		IInjector injector = InjectorFactory.getDefault();
		injector.addBinding(MyBinding.class);

		wrapSetup(); // do it in a separate method to ease GC
		System.gc();
		System.runFinalization();
		System.gc();
		helper.sendEvent("e4/test/event1", "wrong");
		assertFalse(testFailed); // target would have asserted if it is still
	}

	public void testInjectWildCard() {
		IEclipseContext context = EclipseContextFactory.create();
		final Display d = Display.getDefault();

		context.set(Realm.class, SWTObservables.getRealm(d));
		context.set(UISynchronize.class, new UISynchronize() {

			public void syncExec(Runnable runnable) {
				d.syncExec(runnable);
			}

			public void asyncExec(Runnable runnable) {
				d.asyncExec(runnable);
			}
		});

		InjectStarEvent target = ContextInjectionFactory.make(
				InjectStarEvent.class, context);

		assertEquals(0, target.counter1);
		assertNull(target.data);

		helper.sendEvent("e4/test/eventInjection", "sample");

		assertEquals(1, target.counter1);
		assertEquals("sample", target.data);
	}

	private void wrapSetup() {
		IEclipseContext context = EclipseContextFactory.create();
		InjectTarget target = ContextInjectionFactory.make(InjectTarget.class,
				context);
		helper.sendEvent("e4/test/event1", "event1data");
		assertEquals(1, target.counter1);
		assertEquals("event1data", target.string1);
		target.valid = false;
	}

	private void ensureEventAdminStarted() {
		if (Activator.getDefault().getEventAdmin() == null) {
			Bundle[] bundles = Activator.getDefault().getBundle()
					.getBundleContext().getBundles();
			for (Bundle bundle : bundles) {
				if (!"org.eclipse.equinox.event".equals(bundle
						.getSymbolicName()))
					continue;
				try {
					bundle.start(Bundle.START_TRANSIENT);
				} catch (BundleException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
