
package org.eclipse.ui.views.markers.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DialogBookmarkFilter extends DialogMarkerFilter {

	private DescriptionGroup descriptionGroup;

	private class DescriptionGroup {
		private Label descriptionLabel;

		private Combo combo;

		private Text description;

		private String contains = MarkerMessages.filtersDialog_contains;

		private String doesNotContain = 
			MarkerMessages.filtersDialog_doesNotContain;

		public DescriptionGroup(Composite parent) {
			descriptionLabel = new Label(parent, SWT.NONE);
			descriptionLabel.setFont(parent.getFont());
			descriptionLabel.setText(
				MarkerMessages.filtersDialog_descriptionLabel);

			combo = new Combo(parent, SWT.READ_ONLY);
			combo.setFont(parent.getFont());
			combo.add(contains);
			combo.add(doesNotContain);
			combo.addSelectionListener(new SelectionAdapter(){
	        	@Override
				public void widgetSelected(SelectionEvent e) {
	        		  updateForSelection();
	        	}
	          });
			combo.addTraverseListener(new TraverseListener() {
				@Override
				public void keyTraversed(TraverseEvent e) {
					if (e.detail == SWT.TRAVERSE_ESCAPE
							|| e.detail == SWT.TRAVERSE_RETURN) {
						e.doit = false;
					}
				}
			});

			description = new Text(parent, SWT.SINGLE | SWT.BORDER);
			description.setFont(parent.getFont());
			GridData data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 3;
			description.setLayoutData(data);
			description.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					DialogBookmarkFilter.this.markDirty();
				}
			});
		}

		public boolean getContains() {
			return combo.getSelectionIndex() == combo.indexOf(contains);
		}

		public void setContains(boolean value) {
			if (value) {
				combo.select(combo.indexOf(contains));
			} else {
				combo.select(combo.indexOf(doesNotContain));
			}
		}

		public void setDescription(String text) {
			if (text == null) {
				description.setText(""); //$NON-NLS-1$ 
			} else {
				description.setText(text);
			}
		}

		public String getDescription() {
			return description.getText();
		}

		public void updateEnablement(boolean enabled) {
			descriptionLabel.setEnabled(enabled);
			combo.setEnabled(enabled);
			description.setEnabled(enabled);
		}
	}

	public DialogBookmarkFilter(Shell parentShell, BookmarkFilter[] filters) {
		super(parentShell, filters);
	}

	@Override
	protected void createAttributesArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setFont(parent.getFont());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout(5, false);
		layout.verticalSpacing = 7;
		composite.setLayout(layout);

		descriptionGroup = new DescriptionGroup(composite);
	}

	@Override
	protected void updateFilterFromUI(MarkerFilter filter) {
		super.updateFilterFromUI(filter);

		BookmarkFilter bookmark = (BookmarkFilter) filter;
		bookmark.setContains(descriptionGroup.getContains());
		bookmark.setDescription(descriptionGroup.getDescription().trim());
	
	}
	
	@Override
	protected void updateUIWithFilter(MarkerFilter filter) {
		super.updateUIWithFilter(filter);
		BookmarkFilter bookmark = (BookmarkFilter) filter;
		descriptionGroup.setContains(bookmark.getContains());
		descriptionGroup.setDescription(bookmark.getDescription());
	}

	@Override
	protected void updateEnabledState(boolean enabled) {
		super.updateEnabledState(enabled);
		descriptionGroup.updateEnablement(enabled);
	}
	

	@Override
	protected void resetPressed() {
		descriptionGroup.setContains(BookmarkFilter.DEFAULT_CONTAINS);
		descriptionGroup.setDescription(BookmarkFilter.DEFAULT_DESCRIPTION);

		super.resetPressed();
	}

	@Override
	protected MarkerFilter newFilter(String newName) {

		return new BookmarkFilter(newName);
	}

}
