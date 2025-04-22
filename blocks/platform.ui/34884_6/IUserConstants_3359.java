package org.eclipse.ui.examples.propertysheet;

import java.util.Vector;

import org.eclipse.swt.graphics.RGB;

public class GroupElement extends OrganizationElement {
    public static String P_USERS = "users"; //$NON-NLS-1$

    public static String P_SUBGROUPS = "subgroups"; //$NON-NLS-1$

    public static String P_CONTENTS = "contents"; //$NON-NLS-1$

    private Vector subGroups;

    private Vector users;

    private Vector contents;

    public GroupElement(String name, GroupElement parent) {
        super(name, parent);
    }

    public void add(OrganizationElement userGroup) {
        if (userGroup.isUser() || userGroup.isGroup()) {
            getContents().add(userGroup);
        }
        if (userGroup.isGroup()) {
            getSubGroups().add(userGroup);
            userGroup.setParent(this);
        }
        if (userGroup.isUser()) {
            getUsers().add(userGroup);
            userGroup.setParent(this);
        }

    }

    public GroupElement createSubGroup(String name) {
        GroupElement newGroup = new GroupElement(name, this);
        add(newGroup);
        return newGroup;
    }

    public UserElement createUser(String name) {
        UserElement newUser = new UserElement(name, this);
        add(newUser);
        return newUser;
    }

    public void delete(OrganizationElement userGroup) {
        if (userGroup.isUser() || userGroup.isGroup()) {
            getContents().remove(userGroup);
        }
        if (userGroup.isGroup()) {
            getSubGroups().remove(userGroup);
        }
        if (userGroup.isUser()) {
            getUsers().remove(userGroup);
        }
    }

    public Object[] getChildren(Object o) {
        return getContents().toArray();
    }

    private Vector getContents() {
        if (contents == null)
            contents = new Vector();
        return contents;
    }

    public Object getEditableValue() {
        return this.toString();
    }

    public String getErrorMessage() {
        return null;
    }

    private Vector getSubGroups() {
        if (subGroups == null)
            subGroups = new Vector();
        return subGroups;
    }

    private Vector getUsers() {
        if (users == null)
            users = new Vector();
        return users;
    }

    public boolean isGroup() {
        return true;
    }

    public RGB getForeground(Object element) {
        return null;
    }

    public RGB getBackground(Object element) {
        return null;
    }
}
