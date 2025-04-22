package org.eclipse.jgit.blame;

import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.util.IntList;

public class MyersDiffImpl implements IDiff {

	private static class EditDifference implements IDifference {

		private Edit edit;

		public EditDifference(Edit edit) {
			this.edit = edit;
		}

		public int getStartA() {
			return edit.getBeginA();
		}

		public int getLengthA() {
			return edit.getEndA() - edit.getBeginA();
		}

		public int getStartB() {
			return edit.getBeginB();
		}

		public int getLengthB() {
			return edit.getEndB() - edit.getBeginB();
		}

	}

	public IDifference[] diff(byte[] parentBytes
			byte[] targetBytes
		RawText parentSeq = RawText.FACTORY.create(parentBytes);
		RawText targetSeq = RawText.FACTORY.create(targetBytes);
		MyersDiff myersDiff = new MyersDiff(parentSeq
		EditList edits = myersDiff.getEdits();
		IDifference[] diffs = new IDifference[edits.size()];
		int d = 0;
		for (Edit edit : edits) {
			IDifference newDiff = new EditDifference(edit);
			diffs[d] = newDiff;
			d++;
		}
		return diffs;
	}

}
