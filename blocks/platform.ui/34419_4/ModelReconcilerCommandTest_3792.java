
package org.eclipse.e4.ui.tests.reconciler;

import java.util.Collection;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.commands.MBindingContext;
import org.eclipse.e4.ui.model.application.commands.MBindingTable;
import org.eclipse.e4.ui.model.application.commands.impl.CommandsFactoryImpl;
import org.eclipse.e4.ui.workbench.modeling.ModelDelta;
import org.eclipse.e4.ui.workbench.modeling.ModelReconciler;

public abstract class ModelReconcilerBindingTableTest extends
		ModelReconcilerTest {

	public void testBindingTable_BindingContext_Set() {
		MApplication application = createApplication();

		MBindingTable bindingTable = CommandsFactoryImpl.eINSTANCE
				.createBindingTable();
		application.getBindingTables().add(bindingTable);

		MBindingContext bindingContext = CommandsFactoryImpl.eINSTANCE
				.createBindingContext();
		application.getRootContext().add(bindingContext);

		saveModel();

		ModelReconciler reconciler = createModelReconciler();
		reconciler.recordChanges(application);

		bindingTable.setBindingContext(bindingContext);

		Object state = reconciler.serialize();

		application = createApplication();
		bindingTable = application.getBindingTables().get(0);

		Collection<ModelDelta> deltas = constructDeltas(application, state);

		assertEquals(1, application.getBindingTables().size());
		assertEquals(bindingTable, application.getBindingTables().get(0));
		assertNull(bindingTable.getBindingContext());

		applyAll(deltas);

		MBindingContext restoredBindingContext = bindingTable
				.getBindingContext();
		assertEquals(1, application.getBindingTables().size());
		assertEquals(bindingTable, application.getBindingTables().get(0));
		assertNotNull(restoredBindingContext);
		assertEquals(1, application.getRootContext().size());
		assertEquals(restoredBindingContext, application.getRootContext()
				.get(0));
	}

	public void testBindingTable_BindingContext_Unset() {
		MApplication application = createApplication();

		MBindingTable bindingTable = CommandsFactoryImpl.eINSTANCE
				.createBindingTable();
		application.getBindingTables().add(bindingTable);

		MBindingContext bindingContext = CommandsFactoryImpl.eINSTANCE
				.createBindingContext();
		bindingTable.setBindingContext(bindingContext);
		application.getRootContext().add(bindingContext);

		saveModel();

		ModelReconciler reconciler = createModelReconciler();
		reconciler.recordChanges(application);

		bindingTable.setBindingContext(null);

		Object state = reconciler.serialize();

		application = createApplication();
		bindingTable = application.getBindingTables().get(0);

		Collection<ModelDelta> deltas = constructDeltas(application, state);

		MBindingContext restoredBindingContext = bindingTable
				.getBindingContext();
		assertNotNull(restoredBindingContext);
		assertEquals(1, application.getRootContext().size());
		assertEquals(restoredBindingContext, application.getRootContext()
				.get(0));
		assertEquals(1, application.getBindingTables().size());
		assertEquals(bindingTable, application.getBindingTables().get(0));

		applyAll(deltas);

		assertNull(bindingTable.getBindingContext());
		assertEquals(1, application.getRootContext().size());
		assertEquals(restoredBindingContext, application.getRootContext()
				.get(0));
		assertEquals(1, application.getBindingTables().size());
		assertEquals(bindingTable, application.getBindingTables().get(0));
	}

}
