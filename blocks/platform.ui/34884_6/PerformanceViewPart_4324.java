
package org.eclipse.ui.tests.performance.parts;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.views.markers.internal.ProblemView;

public class PerformanceProblemsView extends ProblemView {

	public Tree getTreeWidget(){
		return getTree();
	}
}
