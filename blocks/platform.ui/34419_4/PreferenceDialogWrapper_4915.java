package org.eclipse.ui.tests.dialogs;


public class NavigatorTestStub {
    private NavigatorTestStub() {
    }

     public static GotoResourceDialog newGotoResourceDialog(Shell parentShell,IResource[] resources) {
     return new GotoResourceDialog(parentShell, resources);
     }
}

