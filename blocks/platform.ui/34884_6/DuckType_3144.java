package org.eclipse.jface.examples.databinding.contentprovider.test;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.Observables;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.databinding.observable.masterdetail.IObservableFactory;
import org.eclipse.core.databinding.observable.set.IObservableSet;
import org.eclipse.core.databinding.observable.set.UnionSet;
import org.eclipse.core.databinding.observable.set.WritableSet;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.databinding.viewers.ObservableSetTreeContentProvider;
import org.eclipse.jface.internal.databinding.provisional.viewers.ViewerLabelProvider;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.viewers.IViewerLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerLabel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class TreeContentProviderTest {

	private Shell shell;
	private TreeViewer tree;

	private AsynchronousTestSet set1;
	private AsynchronousTestSet set2;
	private AsynchronousTestSet set3;

	private UnionSet union;
	private Button randomize;

	public TreeContentProviderTest() {

		set1 = new AsynchronousTestSet();
		set2 = new AsynchronousTestSet();
		set3 = new AsynchronousTestSet();

		union = new UnionSet(new IObservableSet[] { set1, set2, set3 });

		shell = new Shell(Display.getCurrent());

		createTree();

		Composite buttonBar = new Composite(shell, SWT.NONE);
		{
			buttonBar.setLayout(new FillLayout(SWT.HORIZONTAL));
			randomize = new Button(buttonBar, SWT.PUSH);
			randomize.setText("Randomize");
			randomize.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					AsynchronousTestSet.recomputeAll();
					super.widgetSelected(e);
				}
			});

			GridLayoutFactory.fillDefaults().generateLayout(buttonBar);
		}

		GridLayoutFactory.fillDefaults().margins(LayoutConstants.getMargins())
				.generateLayout(shell);

		shell.addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {
				dispose();
			}
		});
	}

	protected void dispose() {
		set1.dispose();
		set2.dispose();
		set3.dispose();
		union.dispose();
	}

	private void createTree() {
		IObservableFactory childrenFactory = new IObservableFactory() {
			@Override
			public IObservable createObservable(Object element) {
				if (element == tree.getInput()) {
					WritableSet topElements = new WritableSet();
					topElements.add(new SimpleNode("Random Set 1", set1));
					topElements.add(new SimpleNode("Random Set 2", set2));
					topElements.add(new SimpleNode("Random Set 3", set3));
					topElements.add(new SimpleNode("Union of the other sets",
							union));
					return topElements;
				}

				if (element instanceof SimpleNode) {
					return Observables
							.proxyObservableSet(((SimpleNode) element)
									.getChildren());
				}

				return null;
			}
		};

		IViewerLabelProvider labelProvider = new ViewerLabelProvider() {
			@Override
			public void updateLabel(ViewerLabel label, Object element) {
				if (element instanceof SimpleNode) {
					SimpleNode node = (SimpleNode) element;

					label.setText(node.getNodeName());
				}

				if (element instanceof Integer) {
					Integer node = (Integer) element;

					label.setText("Integer " + node);
				}
			}
		};

		tree = new TreeViewer(shell, SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		ObservableSetTreeContentProvider contentProvider = new ObservableSetTreeContentProvider(
				childrenFactory, null);

		tree.setContentProvider(contentProvider);
		tree.setLabelProvider(labelProvider);

		tree.setInput(new Object());
	}

	public static void main(String[] args) {
		final Display display = Display.getDefault();
		Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
			@Override
			public void run() {
				TreeContentProviderTest test = new TreeContentProviderTest();
				Shell s = test.getShell();
				s.pack();
				s.setVisible(true);

				while (!s.isDisposed()) {
					if (!display.readAndDispatch())
						display.sleep();
				}
			}
		});
		display.dispose();
	}

	private Shell getShell() {
		return shell;
	}
}
