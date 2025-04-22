package org.eclipse.egit.ui.internal.commit;

public interface ILogicalLineNumberProvider {

	int getLogicalLine(int lineNumber);

	int getMaximum();
}
