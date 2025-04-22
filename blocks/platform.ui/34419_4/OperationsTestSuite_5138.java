
package org.eclipse.ui.tests.operations;

import junit.framework.TestCase;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.commands.operations.DefaultOperationHistory;
import org.eclipse.core.commands.operations.ICompositeOperation;
import org.eclipse.core.commands.operations.IOperationApprover2;
import org.eclipse.core.commands.operations.IOperationHistoryListener;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.commands.operations.IOperationApprover;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.LinearUndoEnforcer;
import org.eclipse.core.commands.operations.ObjectUndoContext;
import org.eclipse.core.commands.operations.OperationHistoryEvent;
import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.core.commands.operations.OperationStatus;
import org.eclipse.core.commands.operations.TriggeredOperations;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.tests.internal.ForcedException;

public class OperationsAPITest extends TestCase {

	static int STRESS_NUM = 5000;

	ObjectUndoContext contextA, contextB, contextC, contextW;
	IOperationHistory history;

	IUndoableOperation op1, op2, op3, op4, op5, op6, localA, localB, localC;
	ICompositeOperation refactor;

	int preExec, postExec, preUndo, postUndo, preRedo, postRedo, add, remove, notOK, changed = 0;
	IOperationHistoryListener listener;

	public OperationsAPITest() {
		super();
	}

	public OperationsAPITest(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		history = new DefaultOperationHistory();
		contextA = new ObjectUndoContext("A");
		contextB = new ObjectUndoContext("B");
		contextC = new ObjectUndoContext("C");
		op1 = new TestOperation("op1");
		op1.addContext(contextA);
		op2 = new TestOperation("op2");
		op2.addContext(contextB);
		op2.addContext(contextC);
		op3 = new TestOperation("op3");
		op3.addContext(contextC);
		op4 = new TestOperation("op4");
		op4.addContext(contextA);
		op5 = new TestOperation("op5");
		op5.addContext(contextB);
		op6 = new TestOperation("op6");
		op6.addContext(contextC);
		op6.addContext(contextA);
		history.execute(op1, null, null);
		history.execute(op2, null, null);
		history.execute(op3, null, null);
		history.execute(op4, null, null);
		history.execute(op5, null, null);
		history.execute(op6, null, null);
		preExec = 0; postExec = 0;
		preUndo = 0; postUndo = 0;
		preRedo = 0; postRedo = 0;
		add = 0; remove = 0; notOK = 0;
		listener = new IOperationHistoryListener() {
			@Override
			public void historyNotification(OperationHistoryEvent event) {
				switch (event.getEventType()) {
				case OperationHistoryEvent.ABOUT_TO_EXECUTE:
					preExec++;
					break;
				case OperationHistoryEvent.ABOUT_TO_UNDO:
					preUndo++;
					break;
				case OperationHistoryEvent.ABOUT_TO_REDO:
					preRedo++;
					break;
				case OperationHistoryEvent.DONE:
					postExec++;
					break;
				case OperationHistoryEvent.UNDONE:
					postUndo++;
					break;
				case OperationHistoryEvent.REDONE:
					postRedo++;
					break;
				case OperationHistoryEvent.OPERATION_ADDED:
					add++;
					break;
				case OperationHistoryEvent.OPERATION_REMOVED:
					remove++;
					break;
				case OperationHistoryEvent.OPERATION_NOT_OK:
					notOK++;
					break;
				case OperationHistoryEvent.OPERATION_CHANGED:
					changed++;
					break;
				}
			}
		};
		history.addOperationHistoryListener(listener);

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		history.removeOperationHistoryListener(listener);
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
	}

	public void testContextDispose() throws ExecutionException {
		assertSame(history.getUndoOperation(contextA), op6);
		assertSame(history.getUndoOperation(contextC), op6);
		history.dispose(contextA, true, true, false);
		assertSame(history.getUndoOperation(contextC), op6);
		assertFalse(op6.hasContext(contextA));
		history.undo(contextC, null, null);
		history.dispose(contextC, true, false, false);
		assertFalse(history.canUndo(contextC));
		assertTrue(history.canRedo(contextC));
		history.redo(contextC, null, null);
		IUndoableOperation[] ops = history.getUndoHistory(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertEquals(ops.length, 3);
		ops = history.getUndoHistory(contextC);
		assertEquals(ops.length, 1);
		ops = history.getUndoHistory(contextB);
		assertEquals(ops.length, 2);
	}

	public void testContextHistories() throws ExecutionException {
		assertSame(history.getUndoOperation(contextA), op6);
		assertSame(history.getUndoOperation(contextB), op5);
		assertSame(history.getUndoOperation(contextC), op6);
		IStatus status = history.undo(contextC, null, null);
		assertTrue("Status should be ok", status.isOK());
		assertSame(history.getRedoOperation(contextC), op6);
		assertSame(history.getUndoOperation(contextC), op3);
		assertTrue("Should be able to redo in c3", history.canRedo(contextC));
		assertTrue("Should be able to redo in c1", history.canRedo(contextA));
		history.redo(contextA, null, null);
		assertSame(history.getUndoOperation(contextC), op6);
		assertSame(history.getUndoOperation(contextA), op6);
	}

	public void testHistoryLimit() throws ExecutionException {
		history.setLimit(contextA, 2);
		assertTrue(history.getUndoHistory(contextA).length == 2);
		history.add(op1);
		assertTrue(history.getUndoHistory(contextA).length == 2);
		history.setLimit(contextB, 1);
		assertTrue(history.getUndoHistory(contextB).length == 1);
		assertFalse(op2.hasContext(contextB));
		history.undo(contextB, null, null);
		assertTrue(history.getRedoHistory(contextB).length == 1);
		assertTrue(history.getUndoHistory(contextB).length == 0);
		history.redo(contextB, null, null);
		assertTrue(history.getRedoHistory(contextB).length == 0);
		assertTrue(history.getUndoHistory(contextB).length == 1);
	}

	public void testLocalHistoryLimits() throws ExecutionException {
		history.setLimit(contextC, 2);
		assertTrue(history.getUndoHistory(contextC).length == 2);
		assertFalse(op2.hasContext(contextC));
		assertTrue(history.getUndoHistory(contextB).length == 2);

		history.setLimit(contextB, 1);
		assertTrue(history.getUndoHistory(contextB).length == 1);
		history.undo(contextB, null, null);
		op2.addContext(contextC);
		history.add(op2);
		assertSame(history.getUndoOperation(contextB), op2);
		assertTrue(history.getUndoHistory(contextB).length == 1);

		history.setLimit(contextA, 0);
		assertTrue(history.getUndoHistory(contextA).length == 0);
		history.add(op1);
		assertTrue(history.getUndoHistory(contextA).length == 0);
	}

	public void testOpenOperation() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		ICompositeOperation batch = new TriggeredOperations(op1, history);
		history.openOperation(batch, IOperationHistory.EXECUTE);
		op1.execute(null, null);
		op2.execute(null, null);
		history.add(op2);
		history.execute(op3, null, null);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("no operations should be in history yet", op == null);
		history.closeOperation(true, true, IOperationHistory.EXECUTE);
		op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("Operation should be batching", op == batch);
		op.removeContext(contextB);
		assertFalse("Operation should not have context", op.hasContext(contextB));
	}

	public void testExceptionDuringOpenOperation() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		IUndoableOperation op = new AbstractOperation("Operation with Exception") {
			@Override
			public IStatus execute(IProgressMonitor monitor, IAdaptable uiInfo) {
				return Status.OK_STATUS;
			}
			@Override
			public IStatus undo(IProgressMonitor monitor, IAdaptable uiInfo) {
				throw new ForcedException("Forced during undo");
			}
			@Override
			public IStatus redo(IProgressMonitor monitor, IAdaptable uiInfo) {
				throw new ForcedException("Forced during redo");
			}
		};

		ICompositeOperation batch = new TriggeredOperations(op, history);
		history.openOperation(batch, IOperationHistory.EXECUTE);
		op.execute(null, null);
		op1.execute(null, null);
		history.add(op1);
		history.execute(op2, null, null);
		history.closeOperation(true, true, IOperationHistory.EXECUTE);
		try {
			batch.undo(null, null);
		} catch (ForcedException e) {
		}

		try {
			history.openOperation(new TriggeredOperations(op3, history), IOperationHistory.EXECUTE);
			history.closeOperation(true, true, IOperationHistory.EXECUTE);
		} catch (IllegalStateException e) {
			assertTrue("IllegalStateException - trying to open an operation before a close", false);
		}
	}

	public void test94459() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		op2.execute(null, null);
		ICompositeOperation batch = new TriggeredOperations(op2, history);
		history.openOperation(batch, IOperationHistory.EXECUTE);
		history.setLimit(contextA, 0);
		op1.execute(null, null);
		history.add(op1);
		history.closeOperation(true, true, IOperationHistory.EXECUTE);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("Operation should be batching", op == batch);
		assertFalse("Operation should not have context", op.hasContext(contextA));
	}

	public void test94459AllContextsEmpty() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		op2.execute(null, null);
		ICompositeOperation batch = new TriggeredOperations(op2, history);
		history.openOperation(batch, IOperationHistory.EXECUTE);
		history.setLimit(contextA, 0);
		history.setLimit(contextB, 0);
		history.setLimit(contextC, 0);
		op1.execute(null, null);
		history.add(op1);
		history.closeOperation(true, true, IOperationHistory.EXECUTE);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("Operation should not have been added", op == null);
	}

	public void test94400() throws ExecutionException {
		UnredoableTestOperation op = new UnredoableTestOperation("troubled op");
		op.addContext(contextA);
		history.execute(op, null, null);
		assertTrue("Operation should be undoable", history.canUndo(contextA));
		history.undo(contextA, null, null);
		assertTrue("Operation should still be in redo history", history.getRedoOperation(contextA) == op);
		assertFalse("Operation should not be disposed", op.disposed);
	}

	public void test123316() throws ExecutionException {
		UnredoableTestOperation op = new UnredoableTestOperation("troubled op");
		op.addContext(contextA);
		history.setLimit(contextA, 0);
		history.execute(op, null, null);
		assertFalse("Should be nothing to undo", history.canUndo(contextA));
		assertTrue("Operation should be disposed", op.disposed);
	}


	public void testUnsuccessfulOpenOperation() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		ICompositeOperation batch = new TriggeredOperations(op1, history);
		history.openOperation(batch, IOperationHistory.EXECUTE);
		op1.execute(null, null);
		op2.execute(null, null);
		history.add(op2);
		history.execute(op3, null, null);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("no operations should be in history yet", op == null);
		history.closeOperation(false, true, IOperationHistory.EXECUTE);
		op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertNull("Unsuccessful operation should not be added to history", op);
		assertTrue("NOT_OK notification should have been received", notOK == 1);
		assertTrue("DONE should not be sent while batching", postExec == 0);
		assertTrue("ADDED should not have been sent while batching", add == 0);
	}

	public void testNotAddedOpenOperation() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		ICompositeOperation batch = new TriggeredOperations(op1, history);
		history.openOperation(batch, IOperationHistory.EXECUTE);
		op1.execute(null, null);
		op2.execute(null, null);
		history.add(op2);
		history.execute(op3, null, null);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("no operations should be in history yet", op == null);
		history.closeOperation(true, false, IOperationHistory.EXECUTE);
		op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertNull("Operation should not be added to history", op);
		assertTrue("DONE notification should have been received", postExec == 1);
		assertTrue("ADDED should not have occurred or be sent while batching", add == 0);
	}

	public void testMultipleOpenOperation() throws ExecutionException {
		boolean failure = false;
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		ICompositeOperation comp1 = new TriggeredOperations(op1, history);
		history.openOperation(comp1, IOperationHistory.EXECUTE);
		op1.execute(null, null);
		op2.execute(null, null);
		history.add(op2);
		history.execute(op3, null, null);
		ICompositeOperation comp2 = new TriggeredOperations(op4, history);
		try {
			history.openOperation(comp2, IOperationHistory.EXECUTE);
		} catch (IllegalStateException e) {
			failure = true;
		}
		assertTrue("Exception should have been thrown for second open operation", failure);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertNull("Unexpected nested open should not add original", op);
		history.closeOperation(true, true, IOperationHistory.EXECUTE);
		op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertSame("First operation should be closed", op, comp1);
	}

	public void testAbortedOpenOperation() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		history.openOperation(new TriggeredOperations(op1, history), IOperationHistory.EXECUTE);
		op1.execute(null, null);
		history.execute(op2, null, null);
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		history.add(op3);
		history.closeOperation(true, true, IOperationHistory.EXECUTE);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("Open operation should be flushed", op == op3);
	}

	public void testOperationApproval() throws ExecutionException {
		history.addOperationApprover(new LinearUndoEnforcer());
		IStatus status = history.undo(contextB, null, null);
		assertTrue(status.isOK());

		assertTrue(history.canUndo(contextB));
		status = history.undo(contextB, null, null);
		assertFalse(status.isOK());

		status = history.undo(contextC, null, null);
		assertTrue(status.isOK());
		status = history.undo(contextC, null, null);
		assertTrue(status.isOK());

		status = history.undo(contextB, null, null);
		assertTrue(status.isOK());

		history.addOperationApprover(new IOperationApprover() {

			@Override
			public IStatus proceedRedoing(IUndoableOperation o, IOperationHistory h, IAdaptable a) {
				return Status.CANCEL_STATUS;
			}
			@Override
			public IStatus proceedUndoing(IUndoableOperation o, IOperationHistory h, IAdaptable a) {
				return Status.CANCEL_STATUS;
			}
		});
		assertFalse(history.redo(contextB, null, null).isOK());
		assertFalse(history.redo(contextC, null, null).isOK());
		assertFalse(history.undo(contextA, null, null).isOK());
		assertFalse(history.undo(contextB, null, null).isOK());
		assertFalse(history.undo(contextC, null, null).isOK());
	}

	public void testOperationFailure() throws ExecutionException {
		history.addOperationApprover(new IOperationApprover() {

			@Override
			public IStatus proceedRedoing(IUndoableOperation o, IOperationHistory h, IAdaptable a) {
				return Status.OK_STATUS;
			}
			@Override
			public IStatus proceedUndoing(IUndoableOperation o, IOperationHistory h, IAdaptable a) {
				if (o == op6) {
					return Status.CANCEL_STATUS;
				}
				if (o == op5) {
					return new OperationStatus(IStatus.ERROR, "org.eclipse.ui.tests", 0, "Error", null);
				}
				return Status.OK_STATUS;
			}
		});

		IStatus status = history.undo(contextC, null, null);
		assertFalse(status.isOK());
		assertSame(history.getUndoOperation(contextC), op6);

		status = history.undo(contextB, null, null);
		assertFalse(status.isOK());

		assertSame(history.getUndoOperation(contextB), op5);
	}

	public void testOperationRedo() throws ExecutionException {
		history.undo(contextB, null, null);
		history.undo(contextB, null, null);
		history.undo(contextC, null, null);
		history.undo(contextC, null, null);
		assertSame(history.getRedoOperation(contextB), op2);
		assertSame(history.getUndoOperation(contextA), op4);
		assertTrue(history.canUndo(contextA));
		assertFalse(history.canUndo(contextB));
		assertFalse(history.canUndo(contextC));
		assertTrue(preUndo == 4);
		assertTrue(postUndo == 4);
		history.redo(contextB, null, null);
		assertTrue(postRedo == 1);
		assertTrue(history.canUndo(contextB));
		assertTrue(history.canUndo(contextC));
	}

	public void testOperationUndo() throws ExecutionException {
		history.undo(contextA, null, null);
		history.undo(contextA, null, null);
		assertSame(history.getRedoOperation(contextA), op4);
		assertSame(history.getUndoOperation(contextA), op1);
		history.undo(contextA, null, null);
		assertTrue(preUndo == 3);
		assertTrue(postUndo == 3);
		assertFalse("Shouldn't be able to undo in c1", history.canUndo(contextA));
		assertTrue("Should be able to undo in c2", history.canUndo(contextB));
		assertTrue("Should be able to undo in c3", history.canUndo(contextC));
	}

	public void testHistoryFactory() {
		IOperationHistory anotherHistory = OperationHistoryFactory.getOperationHistory();
		assertNotNull(anotherHistory);
	}

	public void testOperationChanged() {
		history.operationChanged(op1);
		history.operationChanged(op2);
		history.operationChanged(new TestOperation("New op"));
		assertTrue("should not notify about changes if not in the history", changed == 2);
	}

	private void setup87675() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		contextA = new ObjectUndoContext("A");
		contextB = new ObjectUndoContext("B");
		contextC = new ObjectUndoContext("C");
		contextW = new ObjectUndoContext("W");
		history.addOperationApprover(new LinearUndoEnforcer());

		IUndoableOperation op = new TestOperation("op1a");
		op.addContext(contextA);
		history.execute(op, null, null);
		op = new TestOperation("op1b");
		op.addContext(contextB);
		history.execute(op, null, null);
		op = new TestOperation("op1c");
		op.addContext(contextC);
		history.execute(op, null, null);

		op = new TestOperation("Refactoring");
		op.addContext(contextW);
		op.execute(null, null);
		refactor = new TriggeredOperations(op, history);
		history.openOperation(refactor, IOperationHistory.EXECUTE);
		localA = new TestOperation("op2a");
		localA.addContext(contextA);
		history.execute(localA, null, null);
		localB = new TestOperation("op2b");
		localB.addContext(contextB);
		history.execute(localB, null, null);
		localC = new TestOperation("op2c");
		localC.addContext(contextC);
		history.execute(localC, null, null);

		history.closeOperation(true, true, IOperationHistory.EXECUTE);

		op = new TestOperation("op3c");
		op.addContext(contextC);
		history.execute(op, null, null);
	}

	public void test87675_split() throws ExecutionException {
		setup87675();
		IUndoableOperation op;

		op = history.getUndoOperation(contextA);
		assertTrue("Refactoring should be next op for context A", op == refactor);
		op = history.getUndoOperation(contextB);
		assertTrue("Refactoring should be next op for context B", op == refactor);
		op = history.getUndoOperation(contextW);
		assertTrue("Refactoring should be next op for context W", op == refactor);
		op = history.getUndoOperation(contextC);
		assertFalse("Refactoring should not be next op for context C", op == refactor);

		IStatus status = history.undo(contextW, null, null);
		assertFalse("Undo should not be permitted due to linear conflict", status.isOK());

		history.dispose(contextW, true, true, false);

		op = history.getUndoOperation(contextA);
		assertTrue("Local edit A should be atomic", op == localA);
		op = history.getUndoOperation(contextB);
		assertTrue("Local edit B should be atomic", op == localB);
		op = history.getUndoOperation(contextC);
		assertFalse("Local edit C should not be refactoring edit", op == localC);

		history.undo(contextC, null, null);
		op = history.getUndoOperation(contextC);
		assertTrue("Local edit C should be refactoring edit", op == localC);
	}

	public void test87675_undoredo() throws ExecutionException {
		setup87675();
		IUndoableOperation op;

		history.undo(contextC, null, null);

		history.undo(contextC, null, null);

		op = history.getUndoOperation(contextC);
		assertTrue("Local edit C should be original edit", op.getLabel().equals("op1c"));

		op = history.getUndoOperation(contextB);
		assertTrue("Local edit B should be original edit", op.getLabel().equals("op1b"));

		op = history.getUndoOperation(contextA);
		assertTrue("Local edit A should be original edit", op.getLabel().equals("op1a"));

		op = history.getRedoOperation(contextW);
		assertTrue("operation should have context A", op.hasContext(contextA));
		assertTrue("operation should have context B", op.hasContext(contextB));
		assertTrue("operation should have context C", op.hasContext(contextC));

		history.redo(contextA, null, null);

		op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("operation should have context W", op.hasContext(contextW));

		history.undo(contextW, null, null);

		op = history.getUndoOperation(contextC);
		assertTrue("Local edit C should be original edit", op.getLabel().equals("op1c"));

		op = history.getUndoOperation(contextB);
		assertTrue("Local edit B should be original edit", op.getLabel().equals("op1b"));

		op = history.getUndoOperation(contextA);
		assertTrue("Local edit A should be original edit", op.getLabel().equals("op1a"));

	}

	public void testOperationApprover2() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);

		history.addOperationApprover(new IOperationApprover2() {

			@Override
			public IStatus proceedRedoing(IUndoableOperation o, IOperationHistory h, IAdaptable a) {
				return Status.OK_STATUS;
			}
			@Override
			public IStatus proceedExecuting(IUndoableOperation o, IOperationHistory h, IAdaptable a) {
				if (o == op6) {
					return Status.CANCEL_STATUS;
				}
				return Status.OK_STATUS;
			}
			@Override
			public IStatus proceedUndoing(IUndoableOperation o, IOperationHistory h, IAdaptable a) {
				return Status.OK_STATUS;
			}
		});
		IStatus status = history.execute(op1, null, null);
		assertTrue(status.isOK());
		assertTrue(preExec == 1 && postExec == 1);

		status = history.execute(op6, null, null);
		assertFalse(status.isOK());
		assertTrue(preExec == 1 && postExec == 1);
	}

	public void testReplaceContext() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		TriggeredOperations batch = new TriggeredOperations(op1, history);
		history.openOperation(batch, IOperationHistory.EXECUTE);
		op1.execute(null, null);
		op2.execute(null, null);
		history.add(op2);
		history.execute(op3, null, null);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("no operations should be in history yet", op == null);
		history.closeOperation(true, true, IOperationHistory.EXECUTE);
		op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("Operation should be batching", op == batch);
		IUndoContext contextD = new ObjectUndoContext("D");
		batch.replaceContext(contextC, contextD);
		assertFalse("Operation should not have context", batch.hasContext(contextC));
		assertFalse("Operation should not have context", op1.hasContext(contextC));
		assertFalse("Operation should not have context", op2.hasContext(contextC));
		assertFalse("Operation should not have context", op3.hasContext(contextC));
		batch.replaceContext(contextD, contextC);
		assertTrue("Operation should have context", batch.hasContext(contextC));
		assertFalse("Operation should not have context", op1.hasContext(contextC));
		assertTrue("Operation should have context", op2.hasContext(contextC));
		assertTrue("Operation should have context", op3.hasContext(contextC));

	}

	public void test128117simple() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		ICompositeOperation batch = new TriggeredOperations(op1, history);
		IUndoContext context = new ObjectUndoContext("test");
		batch.addContext(context);
		assertTrue("Operation should have newly added context", batch.hasContext(context));
		history.openOperation(batch, IOperationHistory.EXECUTE);
		op1.execute(null, null);
		op2.execute(null, null);
		history.add(op2);
		history.execute(op3, null, null);
		history.closeOperation(true, true, IOperationHistory.EXECUTE);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("Operation should be the composite", op == batch);
		assertTrue("Operation should have top level context", op.hasContext(context));
		op.removeContext(context);
		assertFalse("Operation should have removed top level context", op.hasContext(context));
	}

	public void test128117complex() throws ExecutionException {
		history.dispose(IOperationHistory.GLOBAL_UNDO_CONTEXT, true, true, false);
		ICompositeOperation batch = new TriggeredOperations(op1, history);
		IUndoContext context = new ObjectUndoContext("test");
		batch.addContext(context);
		assertTrue("Operation should have top level context", batch.hasContext(context));
		history.openOperation(batch, IOperationHistory.EXECUTE);
		op1.execute(null, null);
		op2.execute(null, null);
		history.add(op2);
		history.execute(op3, null, null);
		history.closeOperation(true, true, IOperationHistory.EXECUTE);
		IUndoableOperation op = history.getUndoOperation(IOperationHistory.GLOBAL_UNDO_CONTEXT);
		assertTrue("Operation should be the composite", op == batch);
		op.removeContext(contextB);
		assertFalse("Operation should have removed child context", op.hasContext(contextB));
		assertTrue("Operation should have top level context", op.hasContext(context));
		op.removeContext(context);
		assertFalse("Operation should have removed top level context", op.hasContext(context));
	}

	public void testStressTestAPI() throws ExecutionException {
		history.setLimit(contextA, STRESS_NUM);
		for (int i=0; i < STRESS_NUM; i++) {
			IUndoableOperation op = new TestOperation("test");
			op.addContext(contextA);
			if (i%3 == 0) {
				op.addContext(contextB);
			}
			history.execute(op, null, null);
		}
		for (int i=0; i < STRESS_NUM; i++) {
			if (i%2 == 0) {
				history.undo(contextA, null, null);
			}
			if (i%5 == 0) {
				history.redo(contextA, null, null);
			}
		}
	}

	public void test159305() throws ExecutionException {
		final int [] approvalCount = new int[1];
		IOperationApprover approver;
		approver = new IOperationApprover() {
			@Override
			public IStatus proceedUndoing(IUndoableOperation op, IOperationHistory history, IAdaptable uiInfo) {
				approvalCount[0]++;
				return Status.OK_STATUS;
			}
			@Override
			public IStatus proceedRedoing(IUndoableOperation op, IOperationHistory history, IAdaptable uiInfo) {
				approvalCount[0]--;
				return Status.OK_STATUS;
			}
		};
		history.addOperationApprover(approver);
		history.undo(contextB, null, null);
		assertTrue("Operation approver should run only once for linear undo", approvalCount[0] == 1);
		history.redo(contextB, null, null);
		assertTrue("Operation approver should run only once for linear redo", approvalCount[0] == 0);

		history.undoOperation(op5, null, null);
		assertTrue("Operation approver should run only once for direct undo", approvalCount[0]== 1);
		history.redoOperation(op5, null, null);
		assertTrue("Operation approver should run only once for direct redo", approvalCount[0]== 0);

		history.removeOperationApprover(approver);
	}

}
