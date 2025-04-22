
package org.eclipse.jgit.diff;

import java.util.ArrayList;

public class EditList extends ArrayList<Edit> {
	private static final long serialVersionUID = 1L;

	public static EditList singleton(Edit edit) {
		EditList res = new EditList(1);
		res.add(edit);
		return res;
	}

	public EditList() {
		super(16);
	}

	public EditList(int capacity) {
		super(capacity);
	}

	@Override
	public String toString() {
	}
}
