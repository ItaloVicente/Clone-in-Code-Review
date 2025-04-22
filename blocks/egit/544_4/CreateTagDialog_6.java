package org.eclipse.egit.ui.internal.dialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

public class CommitCombo extends Composite {

	private final List<ComboCommitEnt> commits;

	private final Combo combo;

	private class ComboCommitEnt {

		private final String message;

		private final ObjectId objectId;

		public ComboCommitEnt(ObjectId objecId, String message) {
			this.objectId = objecId;
			this.message = message;
		}

	}

	private class CommitContentProposalProvider implements
			IContentProposalProvider {

		public IContentProposal[] getProposals(String contents, int position) {
			List<IContentProposal> list = new ArrayList<IContentProposal>();
			Pattern pattern = Pattern.compile(contents,
					Pattern.CASE_INSENSITIVE);
			for (int i = 0; i < commits.size(); i++) {
				String message = commits.get(i).message;
				if (message.length() >= contents.length()
						&& pattern.matcher(message).find()) {
					list.add(makeContentProposal(message));
				}
			}
			return list.toArray(new IContentProposal[] {});
		}

		private IContentProposal makeContentProposal(final String proposal) {
			return new IContentProposal() {
				public String getContent() {
					return proposal;
				}

				public String getDescription() {
					return null;
				}

				public String getLabel() {
					return null;
				}

				public int getCursorPosition() {
					return proposal.length();
				}
			};
		}
	}

	public CommitCombo(Composite parent, int style) {
		super(parent, style);

		combo = new Combo(this, style | SWT.DROP_DOWN);
		commits = new ArrayList<ComboCommitEnt>();

		setLayout(GridLayoutFactory.swtDefaults().create());
		setLayoutData(GridDataFactory.fillDefaults().create());

		GridData totalLabelData = new GridData();
		totalLabelData.horizontalAlignment = SWT.FILL;
		totalLabelData.grabExcessHorizontalSpace = true;
		combo.setLayoutData(totalLabelData);

		ContentProposalAdapter adapter = new ContentProposalAdapter(combo,
				new ComboContentAdapter(), new CommitContentProposalProvider(),
				null, null);
		adapter.setPropagateKeys(true);
		adapter
				.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
	}

	public void add(RevCommit revCommit) {
		assert null != revCommit;
		checkWidget();

		String shortSha1 = revCommit.getName().substring(0, 6);
		String message = shortSha1 + ": " + revCommit.getShortMessage(); //$NON-NLS-1$
		combo.add(message);
		commits.add(new ComboCommitEnt(revCommit.getId(), message));
	}

	public ObjectId getItem(int index) {
		checkWidget();

		if (!(0 <= index && index < commits.size())) {
			SWT.error(SWT.ERROR_INVALID_RANGE);
		}
		return commits.get(index).objectId;
	}

	public int getSelectedIndex() {
		return combo.getSelectionIndex();
	}

	public ObjectId getValue() {
		int selectionIndex = combo.getSelectionIndex();
		return -1 != selectionIndex ? getItem(selectionIndex) : null;
	}

	public void setSelectedElement(ObjectId objectId) {
		if (null == objectId) {
			return;
		}

		for (int i = 0; i < commits.size(); i++)
			if (objectId.equals(commits.get(i).objectId)) {
				combo.select(i);
				break;
			}
	}

}
