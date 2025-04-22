package org.eclipse.egit.ui.internal.history;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.egit.ui.Activator;
import org.eclipse.egit.ui.UIPreferences;
import org.eclipse.egit.ui.internal.UIIcons;
import org.eclipse.egit.ui.internal.UIText;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.preference.IPersistentPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevFlag;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class FindToolbar extends Composite {

	public interface StatusListener {

		public void setMessage(FindToolbar originator, String text);
	}

	public static final int PREFS_FINDIN_ALL = 0;

	private static final int PREFS_FINDIN_COMMENTS = 1;

	private static final int PREFS_FINDIN_AUTHOR = 2;

	private static final int PREFS_FINDIN_COMMITID = 3;

	private static final int PREFS_FINDIN_COMMITTER = 4;

	private static final int PREFS_FINDIN_REFERENCE = 5;

	private Color errorBackgroundColor;

	private final FindResults findResults;

	private IFindListener listener;

	private IPersistentPreferenceStore store = (IPersistentPreferenceStore) Activator.getDefault().getPreferenceStore();

	private List<Listener> eventList = new ArrayList<>();

	private Table historyTable;

	private SWTCommit[] fileRevisions;

	private Text patternField;

	private ModifyListener patternModifyListener;

	private Action findNextAction;

	private Action findPreviousAction;

	private String lastErrorPattern;

	private ToolItem prefsDropDown;

	private Menu prefsMenu;

	private MenuItem caseItem;

	private MenuItem allItem;

	private MenuItem commitIdItem;

	private MenuItem commentsItem;

	private MenuItem authorItem;

	private MenuItem committerItem;

	private MenuItem referenceItem;

	private Image allIcon;

	private Image commitIdIcon;

	private Image commentsIcon;

	private Image authorIcon;

	private Image committerIcon;

	private Image branchesIcon;

	private FindToolbarJob job;

	private int currentPosition = -1;

	private ObjectId preselect;

	private CopyOnWriteArrayList<StatusListener> layoutListeners = new CopyOnWriteArrayList<>();

	public FindToolbar(Composite parent) {
		super(parent, SWT.NULL);
		findResults = new FindResults();
		listener = createFindListener();
		findResults.addFindListener(listener);
		setBackground(null);
		createToolbar();
	}

	private void createToolbar() {
		errorBackgroundColor = new Color(getDisplay(), new RGB(255, 150, 150));
		ResourceManager resourceManager = Activator.getDefault()
				.getResourceManager();
		allIcon = UIIcons.getImage(resourceManager, UIIcons.SEARCH_COMMIT);
		commitIdIcon = UIIcons.getImage(resourceManager,
				UIIcons.ELCL16_ID);
		commentsIcon = UIIcons.getImage(resourceManager,
				UIIcons.ELCL16_COMMENTS);
		authorIcon = UIIcons.getImage(resourceManager, UIIcons.ELCL16_AUTHOR);
		committerIcon = UIIcons.getImage(resourceManager,
				UIIcons.ELCL16_COMMITTER);
		branchesIcon = UIIcons.getImage(resourceManager, UIIcons.BRANCHES);
		GridLayout findLayout = new GridLayout();
		findLayout.marginHeight = 0;
		findLayout.marginBottom = 1;
		findLayout.marginWidth = 0;
		findLayout.numColumns = 5;
		setLayout(findLayout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));

		patternField = new Text(this,
				SWT.SEARCH | SWT.ICON_CANCEL | SWT.ICON_SEARCH);
		GridData findTextData = new GridData(SWT.FILL, SWT.LEFT, true, false);
		findTextData.minimumWidth = 150;
		patternField.setLayoutData(findTextData);
		patternField.setMessage(UIText.HistoryPage_findbar_find_msg);
		patternField.setTextLimit(100);
		patternField.setFont(JFaceResources.getDialogFont());
		ToolBarManager manager = new ToolBarManager(SWT.HORIZONTAL);
		findNextAction = new Action() {
			@Override
			public void run() {
				findNext();
			}
		};
		findNextAction.setImageDescriptor(UIIcons.ELCL16_NEXT);
		findNextAction.setText(UIText.HistoryPage_findbar_next);
		findNextAction.setToolTipText(UIText.FindToolbar_NextTooltip);
		findNextAction.setEnabled(false);
		manager.add(findNextAction);
		findPreviousAction = new Action() {
			@Override
			public void run() {
				findPrevious();
			}
		};
		findPreviousAction.setImageDescriptor(UIIcons.ELCL16_PREVIOUS);
		findPreviousAction.setText(UIText.HistoryPage_findbar_previous);
		findPreviousAction.setToolTipText(UIText.FindToolbar_PreviousTooltip);
		findPreviousAction.setEnabled(false);
		manager.add(findPreviousAction);
		final ToolBar toolBar = manager.createControl(this);

		prefsDropDown = new ToolItem(toolBar, SWT.DROP_DOWN);
		prefsMenu = new Menu(getShell(), SWT.POP_UP);
		caseItem = new MenuItem(prefsMenu, SWT.CHECK);
		caseItem.setText(UIText.HistoryPage_findbar_ignorecase);
		new MenuItem(prefsMenu, SWT.SEPARATOR);
		allItem = createFindInMenuItem();
		allItem.setText(UIText.HistoryPage_findbar_all);
		allItem.setImage(allIcon);
		commentsItem = createFindInMenuItem();
		commentsItem.setText(UIText.HistoryPage_findbar_comments);
		commentsItem.setImage(commentsIcon);
		authorItem = createFindInMenuItem();
		authorItem.setText(UIText.HistoryPage_findbar_author);
		authorItem.setImage(authorIcon);
		commitIdItem = createFindInMenuItem();
		commitIdItem.setText(UIText.HistoryPage_findbar_commit);
		commitIdItem.setImage(commitIdIcon);
		committerItem = createFindInMenuItem();
		committerItem.setText(UIText.HistoryPage_findbar_committer);
		committerItem.setImage(committerIcon);
		referenceItem = createFindInMenuItem();
		referenceItem.setText(UIText.HistoryPage_findbar_reference);
		referenceItem.setImage(branchesIcon);

		prefsDropDown.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (event.detail == SWT.ARROW) {
					Rectangle itemBounds = prefsDropDown.getBounds();
					Point point = toolBar.toDisplay(itemBounds.x, itemBounds.y
							+ itemBounds.height);
					prefsMenu.setLocation(point);
					prefsMenu.setVisible(true);
				} else {
					if (allItem.getSelection())
						selectFindInItem(commentsItem);
					else if (commentsItem.getSelection())
						selectFindInItem(authorItem);
					else if (authorItem.getSelection())
						selectFindInItem(commitIdItem);
					else if (commitIdItem.getSelection())
						selectFindInItem(committerItem);
					else if (committerItem.getSelection())
						selectFindInItem(referenceItem);
					else if (referenceItem.getSelection())
						selectFindInItem(allItem);
				}
			}
		});

		patternModifyListener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				final FindToolbarJob finder = createFinder();
				finder.setUser(false);
				finder.schedule(200);
			}
		};

		patternField.addModifyListener(patternModifyListener);

		patternField.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail != SWT.ICON_CANCEL
						&& !patternField.getText().isEmpty()) {
					final FindToolbarJob finder = createFinder();
					finder.setUser(false);
					finder.schedule();
				}
			}
		});

		patternField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.ARROW_DOWN) {
					findNext();
				} else if (e.keyCode == SWT.ARROW_UP) {
					findPrevious();
				}
			}
		});

		caseItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				store.setValue(UIPreferences.FINDTOOLBAR_IGNORE_CASE,
						caseItem.getSelection());
				if (store.needsSaving()){
					try {
						store.save();
					} catch (IOException e1) {
						Activator.handleError(e1.getMessage(), e1, false);
					}
				}
				clear();
			}
		});
		caseItem.setSelection(store
				.getBoolean(UIPreferences.FINDTOOLBAR_IGNORE_CASE));

		int selectedPrefsItem = store.getInt(UIPreferences.FINDTOOLBAR_FIND_IN);
		if (selectedPrefsItem == PREFS_FINDIN_ALL)
			selectFindInItem(allItem);
		else if (selectedPrefsItem == PREFS_FINDIN_COMMENTS)
			selectFindInItem(commentsItem);
		else if (selectedPrefsItem == PREFS_FINDIN_AUTHOR)
			selectFindInItem(authorItem);
		else if (selectedPrefsItem == PREFS_FINDIN_COMMITID)
			selectFindInItem(commitIdItem);
		else if (selectedPrefsItem == PREFS_FINDIN_COMMITTER)
			selectFindInItem(committerItem);
		else if (selectedPrefsItem == PREFS_FINDIN_REFERENCE)
			selectFindInItem(referenceItem);

		registerDisposal();
	}

	private void registerDisposal() {
		addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				findResults.removeFindListener(listener);
				findResults.clear();
				listener = null;
				if (job != null) {
					job.cancel();
					job = null;
				}
				prefsMenu.dispose();
				errorBackgroundColor.dispose();
				if (historyTable != null && !historyTable.isDisposed()) {
					historyTable.clearAll();
				}
			}
		});
	}

	public void setPreselect(ObjectId commitId) {
		preselect = commitId;
	}

	@Override
	public boolean setFocus() {
		return patternField.setFocus();
	}

	public void setText(String text, boolean search) {
		if (!search) {
			patternField.removeModifyListener(patternModifyListener);
		}
		patternField.setText(text);
		if (!search) {
			patternField.addModifyListener(patternModifyListener);
		}
	}

	public void setText(String text) {
		setText(text, false);
	}

	public String getText() {
		return patternField.getText();
	}

	@Override
	public void addListener(int evtType, Listener mouseListener) {
		patternField.addListener(evtType, mouseListener);
	}

	@Override
	public void removeListener(int evtType, Listener mouseListener) {
		patternField.removeListener(evtType, mouseListener);
	}

	@Override
	public void addKeyListener(KeyListener keyListener) {
		patternField.addKeyListener(keyListener);
	}

	@Override
	public void removeKeyListener(KeyListener keyListener) {
		patternField.removeKeyListener(keyListener);
	}

	public void addStatusListener(StatusListener layoutListener) {
		layoutListeners.addIfAbsent(layoutListener);
	}

	public void removeStatusListener(StatusListener layoutListener) {
		layoutListeners.remove(layoutListener);
	}

	private void notifyStatus(String text) {
		for (StatusListener l : layoutListeners) {
			l.setMessage(this, text);
		}
	}

	private void findNext() {
		find(true);
	}

	private void findPrevious() {
		find(false);
	}

	private void find(boolean next) {
		if (patternField.getText().length() > 0 && findResults.size() == 0) {
			final FindToolbarJob finder = createFinder();
			finder.setUser(false);
			finder.schedule();
			patternField.setSelection(0, 0);
		} else {
			int currentIx = historyTable.getSelectionIndex();
			int newIx = -1;
			if (next) {
				newIx = findResults.getIndexAfter(currentIx);
				if (newIx == -1) {
					newIx = findResults.getFirstIndex();
				}
			} else {
				newIx = findResults.getIndexBefore(currentIx);
				if (newIx == -1) {
					newIx = findResults.getLastIndex();
				}
			}
			notifyListeners(newIx);

			String current = null;
			currentPosition = findResults.getMatchNumberFor(newIx);
			if (currentPosition == -1) {
				current = "-"; //$NON-NLS-1$
			} else {
				current = String.valueOf(currentPosition);
			}
			notifyStatus(current + '/' + findResults.size());
		}
	}

	private MenuItem createFindInMenuItem() {
		final MenuItem menuItem = new MenuItem(prefsMenu, SWT.RADIO);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectFindInItem(menuItem);
			}
		});
		return menuItem;
	}

	private void selectFindInItem(final MenuItem menuItem) {
		if (menuItem == allItem)
			selectFindInItem(menuItem, PREFS_FINDIN_ALL, allIcon,
					UIText.HistoryPage_findbar_changeto_comments);
		else if (menuItem == commentsItem)
			selectFindInItem(menuItem, PREFS_FINDIN_COMMENTS, commentsIcon,
					UIText.HistoryPage_findbar_changeto_author);
		else if (menuItem == authorItem)
			selectFindInItem(menuItem, PREFS_FINDIN_AUTHOR, authorIcon,
					UIText.HistoryPage_findbar_changeto_commit);
		else if (menuItem == commitIdItem)
			selectFindInItem(menuItem, PREFS_FINDIN_COMMITID, commitIdIcon,
					UIText.HistoryPage_findbar_changeto_committer);
		else if (menuItem == committerItem)
			selectFindInItem(menuItem, PREFS_FINDIN_COMMITTER, committerIcon,
					UIText.HistoryPage_findbar_changeto_reference);
		else if (menuItem == referenceItem)
			selectFindInItem(menuItem, PREFS_FINDIN_REFERENCE, branchesIcon,
					UIText.HistoryPage_findbar_changeto_all);
	}

	private void selectFindInItem(MenuItem menuItem, int preferenceValue,
			Image dropDownIcon, String dropDownToolTip) {
		prefsDropDown.setImage(dropDownIcon);
		prefsDropDown.setToolTipText(dropDownToolTip);
		findInPreferenceChanged(preferenceValue, menuItem);
	}

	private void findInPreferenceChanged(int findin, MenuItem item) {
		store.setValue(UIPreferences.FINDTOOLBAR_FIND_IN, findin);
		if (store.needsSaving()){
			try {
				store.save();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, false);
			}
		}
		allItem.setSelection(false);
		commitIdItem.setSelection(false);
		commentsItem.setSelection(false);
		authorItem.setSelection(false);
		committerItem.setSelection(false);
		referenceItem.setSelection(false);
		item.setSelection(true);
		clear();
	}

	private FindToolbarJob createFinder() {
		if (job != null) {
			job.cancel();
		}
		final String currentPattern = patternField.getText();

		job = new FindToolbarJob(MessageFormat
				.format(UIText.HistoryPage_findbar_find, currentPattern),
				findResults);
		job.pattern = currentPattern;
		job.fileRevisions = fileRevisions;
		job.ignoreCase = caseItem.getSelection();
		if (allItem.getSelection()) {
			job.findInCommitId = true;
			job.findInComments = true;
			job.findInAuthor = true;
			job.findInCommitter = true;
			job.findInReference = true;
		} else {
			job.findInCommitId = commitIdItem.getSelection();
			job.findInComments = commentsItem.getSelection();
			job.findInAuthor = authorItem.getSelection();
			job.findInCommitter = committerItem.getSelection();
			job.findInReference = referenceItem.getSelection();
		}
		job.addJobChangeListener(new JobChangeAdapter() {

			private final FindToolbarJob myJob = job;

			@Override
			public void done(final IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							if (myJob != job
									|| myJob.fileRevisions != fileRevisions) {
								return;
							}
							if (!isDisposed()) {
								findCompletionUpdate(currentPattern,
										findResults.isOverflow());
							}
						}
					});
				}
			}
		});
		return job;
	}

	void setInput(final RevFlag hFlag, final Table historyTable,
			final SWTCommit[] commitArray) {
		if (job != null) {
			job.cancel();
		}
		this.fileRevisions = commitArray;
		this.historyTable = historyTable;
		findResults.setHighlightFlag(hFlag);
	}

	private void findCompletionUpdate(String pattern, boolean overflow) {
		int total = findResults.size();
		String label;
		if (total > 0) {
			String position = (currentPosition < 0) ? "1" //$NON-NLS-1$
					: Integer.toString(currentPosition);
			if (overflow) {
				label = UIText.HistoryPage_findbar_exceeded + ' ' + position
						+ '/' + total;
			} else {
				label = position + '/' + total;
			}
			if (currentPosition < 0) {
				currentPosition = 1;
				int ix = findResults.getFirstIndex();
				notifyListeners(ix);
			}
			patternField.setBackground(null);
			findNextAction.setEnabled(total > 1);
			findPreviousAction.setEnabled(total > 1);
			lastErrorPattern = null;
		} else {
			currentPosition = -1;
			if (pattern.length() > 0) {
				patternField.setBackground(errorBackgroundColor);
				label = UIText.HistoryPage_findbar_notFound;
				if (lastErrorPattern == null
						|| !lastErrorPattern.startsWith(pattern)) {
					getDisplay().beep();
					findNextAction.setEnabled(false);
					findPreviousAction.setEnabled(false);
				}
				lastErrorPattern = pattern;
			} else {
				patternField.setBackground(null);
				label = ""; //$NON-NLS-1$
				findNextAction.setEnabled(false);
				findPreviousAction.setEnabled(false);
				lastErrorPattern = null;
			}
		}
		historyTable.clearAll();

		if (overflow) {
			Display display = getDisplay();
			display.beep();
		}
		notifyStatus(label);
	}

	void clear() {
		if (!isDisposed()) {
			patternField.setBackground(null);
			if (patternField.getText().length() > 0) {
				patternField.selectAll();
			}
		}
		lastErrorPattern = null;

		if (job != null) {
			job.cancel();
			job = null;
		}

		findResults.clear();
	}

	private void notifyListeners(int index) {
		if (index >= 0) {
			Event event = new Event();
			event.type = SWT.Selection;
			event.index = index;
			event.widget = this;
			event.data = fileRevisions[index];
			for (Listener toNotify : eventList) {
				toNotify.handleEvent(event);
			}
		}
	}

	public void addSelectionListener(Listener selectionListener) {
		eventList.add(selectionListener);
	}

	public void removeSelectionListener(Listener selectionListener) {
		eventList.remove(selectionListener);
	}

	private IFindListener createFindListener() {
		return new IFindListener() {

			private static final long UPDATE_INTERVAL = 200L; // ms

			private long lastUpdate = 0L;

			@Override
			public void itemAdded(final int index, RevObject rev) {
				long now = System.currentTimeMillis();
				if (preselect != null && preselect.equals(rev.getId())
						|| preselect == null && currentPosition < 0) {
					currentPosition = findResults.getMatchNumberFor(index);
					preselect = null;
					getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							notifyListeners(index);
						}
					});
				}
				if (now - lastUpdate > UPDATE_INTERVAL && !isDisposed()) {
					final boolean firstUpdate = lastUpdate == 0L;
					lastUpdate = now;
					getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							if (isDisposed()) {
								return;
							}
							int total = findResults.size();
							if (total > 0) {
								String label;
								if (currentPosition == -1) {
									label = "-/" + total; //$NON-NLS-1$
								} else {
									label = Integer.toString(currentPosition)
											+ '/' + total;
								}
								findNextAction.setEnabled(total > 1);
								findPreviousAction.setEnabled(total > 1);
								patternField.setBackground(null);
								if (firstUpdate) {
									historyTable.clearAll();
								}
								notifyStatus(label);
							} else {
								clear();
							}
						}
					});
				}
			}

			@Override
			public void cleared() {
				lastUpdate = 0L;
				if (Display.getCurrent() == null) {
					getDisplay().asyncExec(new Runnable() {

						@Override
						public void run() {
							clear();
						}
					});
				} else {
					clear();
				}
			}

			private void clear() {
				currentPosition = -1;
				if (!isDisposed()) {
					findNextAction.setEnabled(false);
					findPreviousAction.setEnabled(false);
					notifyStatus(""); //$NON-NLS-1$
				}
				if (historyTable != null && !historyTable.isDisposed()) {
					historyTable.clearAll();
				}
			}
		};
	}

}
