	private TestElement testElement1;
	private TestElement testElement2;
	private TestElement testElement3;
	private StructuredSelection expectedSelection;
	private StructuredSelection expectedPostSelection;
	protected Runnable preRemoveTableItemHookExecutable;

	public static class TableTestLabelProvider extends TestLabelProvider implements ITableLabelProvider {
