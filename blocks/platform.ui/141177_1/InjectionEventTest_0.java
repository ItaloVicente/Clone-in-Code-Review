package org.eclipse.e4.ui.tests.model.test.util;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.e4.core.internal.di.osgi.ProviderHelper;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.internal.workbench.swt.E4Application;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.swt.widgets.Display;
import org.junit.rules.ExternalResource;

public class DefaultTestContext extends ExternalResource implements IEclipseContext {

	private IEclipseContext context;

	@Override
	protected void before() throws Throwable {
		context = E4Application.createDefaultContext();
		context.set(IWorkbench.PRESENTATION_URI_ARG, PartRenderingEngine.engineURI);

		final Display d = Display.getDefault();
		context.set(UISynchronize.class, new UISynchronize() {
			@Override
			public void syncExec(Runnable runnable) {
				d.syncExec(runnable);
			}

			@Override
			public void asyncExec(Runnable runnable) {
				d.asyncExec(runnable);
			}
		});
		ContextInjectionFactory.setDefault(context);

		ExtendedObjectSupplier supplier = ProviderHelper.findProvider(UIEventTopic.class.getName(), null);
		ContextInjectionFactory.inject(supplier, context);
	}

	@Override
	protected void after() {
		ContextInjectionFactory.setDefault(null);

		ExtendedObjectSupplier supplier = ProviderHelper.findProvider(UIEventTopic.class.getName(), null);
		ContextInjectionFactory.uninject(supplier, context);

		context.dispose();
	}

	@Override
	public boolean containsKey(String name) {
		return context.containsKey(name);
	}

	@Override
	public boolean containsKey(Class<?> clazz) {
		return context.containsKey(clazz);
	}

	@Override
	public Object get(String name) {
		return context.get(name);
	}

	@Override
	public <T> T get(Class<T> clazz) {
		return context.get(clazz);
	}

	@Override
	public Object getLocal(String name) {
		return context.getLocal(name);
	}

	@Override
	public <T> T getLocal(Class<T> clazz) {
		return context.getLocal(clazz);
	}

	@Override
	public void remove(String name) {
		context.remove(name);
	}

	@Override
	public void remove(Class<?> clazz) {
		context.remove(clazz);
	}

	@Override
	public void runAndTrack(RunAndTrack runnable) {
		context.runAndTrack(runnable);
	}

	@Override
	public void set(String name, Object value) {
		context.set(name, value);
	}

	@Override
	public <T> void set(Class<T> clazz, T value) {
		context.set(clazz, value);
	}

	@Override
	public void modify(String name, Object value) {
		context.modify(name, value);
	}

	@Override
	public <T> void modify(Class<T> clazz, T value) {
		context.modify(clazz, value);
	}

	@Override
	public void declareModifiable(String name) {
		context.declareModifiable(name);
	}

	@Override
	public void declareModifiable(Class<?> clazz) {
		context.declareModifiable(clazz);
	}

	@Override
	public void processWaiting() {
		context.processWaiting();
	}

	@Override
	public IEclipseContext createChild() {
		return context.createChild();
	}

	@Override
	public IEclipseContext createChild(String name) {
		return context.createChild(name);
	}

	@Override
	public IEclipseContext getParent() {
		return context.getParent();
	}

	@Override
	public void setParent(IEclipseContext parentContext) {
		context.setParent(parentContext);
	}

	@Override
	public void activate() {
		context.activate();
	}

	@Override
	public void deactivate() {
		context.deactivate();
	}

	@Override
	public void activateBranch() {
		context.activateBranch();
	}

	@Override
	public IEclipseContext getActiveChild() {
		return context.getActiveChild();
	}

	@Override
	public IEclipseContext getActiveLeaf() {
		return context.getActiveLeaf();
	}

	@Override
	public void dispose() {
		context.dispose();
	}

	@Override
	public <T> T getActive(Class<T> clazz) {
		return context.getActive(clazz);
	}

	@Override
	public Object getActive(String name) {
		return context.getActive(name);
	}

}
