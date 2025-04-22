package org.eclipse.egit.ui.internal.selection;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.contexts.RunAndTrack;
import org.eclipse.egit.ui.Activator;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISources;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.MultiPageEditorPart;

public class RepositoryStateCache {

	private enum RepositoryItem {
		CONFIG, HEAD, HEAD_REF, FULL_BRANCH_NAME, STATE
	}

	public static final RepositoryStateCache INSTANCE = new RepositoryStateCache();

	private static final Object NOTHING = new Object();

	private final AtomicBoolean stopped = new AtomicBoolean();

	private final Map<File, Map<RepositoryItem, Object>> cache = new ConcurrentHashMap<>();

	private RepositoryStateCache() {
	}

	public void initialize() {
		IEclipseContext applicationContext = PlatformUI.getWorkbench()
				.getService(IEclipseContext.class);
		applicationContext.runAndTrack(new ContextListener(stopped, cache));
	}

	public void dispose() {
		stopped.set(true);
	}

	private static class ContextListener extends RunAndTrack {

		private AtomicBoolean stopped;

		private Map<?, ?> cache;

		ContextListener(AtomicBoolean stopped, Map<?, ?> cache) {
			super();
			this.stopped = stopped;
			this.cache = cache;
		}

		private Object lastSelection;

		private Object lastMenuSelection;

		@Override
		public boolean changed(IEclipseContext context) {
			if (stopped.get()) {
				cache.clear();
				return false;
			}
			Object selection = context
					.get(ISources.ACTIVE_CURRENT_SELECTION_NAME);
			if (selection instanceof ITextSelection) {
				selection = getInput(context);
			}
			Object menuSelection = context
					.getActive(ISources.ACTIVE_MENU_SELECTION_NAME);
			if (menuSelection instanceof ITextSelection) {
				menuSelection = getInput(context);
			}
			if (selection != lastSelection
					|| menuSelection != lastMenuSelection) {
				cache.clear();
			}
			lastSelection = selection;
			lastMenuSelection = menuSelection;
			return true;
		}

		private Object getInput(IEclipseContext context) {
			Object[] input = { null };
			runExternalCode(() -> {
				IEditorInput e = getEditorInput(context);
				input[0] = e != null ? e : StructuredSelection.EMPTY;
			});
			return input[0];
		}

		private IEditorInput getEditorInput(IEclipseContext context) {
			Object part = context.get(ISources.ACTIVE_PART_NAME);
			if (!(part instanceof IEditorPart)) {
				return null;
			}
			Object object = context.get(ISources.ACTIVE_EDITOR_INPUT_NAME);
			Object editor = context.get(ISources.ACTIVE_EDITOR_NAME);
			if (editor instanceof MultiPageEditorPart) {
				Object nestedEditor = ((MultiPageEditorPart) editor)
						.getSelectedPage();
				if (nestedEditor instanceof IEditorPart) {
					object = ((IEditorPart) nestedEditor).getEditorInput();
				}
			}
			if (!(object instanceof IEditorInput)
					&& (editor instanceof IEditorPart)) {
				object = ((IEditorPart) editor).getEditorInput();
			}
			if (object instanceof IEditorInput) {
				return (IEditorInput) object;
			}
			return null;
		}
	}

	private Map<RepositoryItem, Object> getItems(Repository repository) {
		return cache.computeIfAbsent(repository.getDirectory(),
				gitDir -> new ConcurrentHashMap<>());
	}

	public StoredConfig getConfig(Repository repository) {
		Object value = getItems(repository).computeIfAbsent(
				RepositoryItem.CONFIG, key -> repository.getConfig());
		return (StoredConfig) value;
	}

	private ObjectId getHead(Repository repository,
			String[] fullName, Ref[] ref) {
		ObjectId head = ObjectId.zeroId();
		String name = null;
		Ref r = null;
		try {
			r = repository.exactRef(Constants.HEAD);
		} catch (IOException e) {
			Activator.logError(e.getLocalizedMessage(), e);
		}
		ref[0] = r;
		if (r != null) {
			if (r.isSymbolic()) {
				name = r.getTarget().getName();
			}
			head = r.getObjectId();
			if (head != null) {
				if (name == null) {
					name = head.name();
				}
			} else {
				head = ObjectId.zeroId();
			}
		}
		fullName[0] = name != null ? name : ""; //$NON-NLS-1$
		return head;
	}

	public ObjectId getHead(Repository repository) {
		if (repository == null) {
			return null;
		}
		Map<RepositoryItem, Object> items = getItems(repository);
		Object value = items.get(RepositoryItem.HEAD);
		if (value == null) {
			String[] fullName = { null };
			Ref[] headRef = { null };
			value = items.computeIfAbsent(RepositoryItem.HEAD,
					key -> getHead(repository, fullName, headRef));
			items.computeIfAbsent(RepositoryItem.FULL_BRANCH_NAME,
					key -> fullName[0]);
			items.computeIfAbsent(RepositoryItem.HEAD_REF,
					key -> headRef[0] == null ? NOTHING : headRef[0]);
		}
		ObjectId head = (ObjectId) value;
		if (head == null || head.equals(ObjectId.zeroId())) {
			return null;
		}
		return head;
	}


	public Ref getHeadRef(Repository repository) {
		if (repository == null) {
			return null;
		}
		Map<RepositoryItem, Object> items = getItems(repository);
		Object value = items.get(RepositoryItem.HEAD_REF);
		if (value == null) {
			String[] fullName = { null };
			Ref[] headRef = { null };
			items.computeIfAbsent(RepositoryItem.HEAD,
					key -> getHead(repository, fullName, headRef));
			items.computeIfAbsent(RepositoryItem.FULL_BRANCH_NAME,
					key -> fullName[0]);
			value = items.computeIfAbsent(RepositoryItem.HEAD_REF,
					key -> headRef[0] == null ? NOTHING : headRef[0]);
		}
		if (value == null || value == NOTHING) {
			return null;
		}
		return (Ref) value;
	}

	public String getFullBranchName(Repository repository) {
		if (repository == null) {
			return null;
		}
		Map<RepositoryItem, Object> items = getItems(repository);
		Object fullBranchName = items.get(RepositoryItem.FULL_BRANCH_NAME);
		if (fullBranchName == null) {
			String[] fullName = { null };
			Ref[] headRef = { null };
			items.computeIfAbsent(RepositoryItem.HEAD,
					key -> getHead(repository, fullName, headRef));
			fullBranchName = items.computeIfAbsent(
					RepositoryItem.FULL_BRANCH_NAME, key -> fullName[0]);
			items.computeIfAbsent(RepositoryItem.HEAD_REF,
					key -> headRef[0] == null ? NOTHING : headRef[0]);
		}
		String name = (String) fullBranchName;
		if (name == null || name.isEmpty()) {
			return null;
		}
		return name;
	}

	public @NonNull RepositoryState getRepositoryState(Repository repository) {
		Object value = getItems(repository).computeIfAbsent(
				RepositoryItem.STATE, key -> repository.getRepositoryState());
		assert value != null; // Keep the compiler happy.
		return (RepositoryState) value;
	}

}
