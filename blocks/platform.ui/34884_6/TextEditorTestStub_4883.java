package org.eclipse.ui.tests.dialogs;

public class TaskListTestStub {
    private TaskListTestStub() {
    }

     public static FiltersDialog newFiltersDialog(Shell parentShell) {
     return new FiltersDialog(parentShell);
     }
}

