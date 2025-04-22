
package org.eclipse.e4.ui.workbench.swt;

public interface ISaveablePart2 extends ISaveablePart {

	public static final int YES = 0;

	public static final int NO = 1;

	public static final int CANCEL = 2;

	public static final int DEFAULT = 3;

	public int promptToSaveOnClose();

}
