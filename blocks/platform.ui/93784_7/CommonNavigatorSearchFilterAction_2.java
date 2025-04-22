package org.eclipse.ui.internal.navigator.filters.search;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.navigator.CommonViewer;
import org.eclipse.ui.navigator.INavigatorViewerDescriptor;

public class CommonNavigatorSearchFilter extends ViewerFilter {

	private final static String ALL_CLASSES_ALLOWED = "*"; //$NON-NLS-1$

	private final static String DEFAULT_ALLOWED_CLASSES = ALL_CLASSES_ALLOWED;

	private final Set<String> allowedClasses;

	private final ConcurrentMap<Class<?>, Boolean> allowedClassesCache = new ConcurrentHashMap<>();

	private final Map<Object, Boolean> elementCache = new HashMap<>();

	private final CommonViewer commonViewer;

	private final Job elementCachePopulationJob;

	private Pattern searchPattern;

	public CommonNavigatorSearchFilter(CommonViewer viewer) {
		this.commonViewer = viewer;
		this.elementCachePopulationJob = createJob();
		this.allowedClasses = getAllowedClassesForViewer(viewer);
	}

	private Job createJob() {
		Job job = Job.create("Calculate Element Filter", monitor -> { //$NON-NLS-1$
			CommonNavigatorSearchFilterHelper helper = CommonNavigatorSearchFilterHelper.getInstance();
			try {
				SubMonitor subMonitor = SubMonitor.convert(monitor);

				Display.getDefault().asyncExec(() -> helper.setBusyImageVisible(commonViewer, true));

				Object input = commonViewer.getInput();
				ITreeContentProvider contentProvider = (ITreeContentProvider) commonViewer.getContentProvider();
				ILabelProvider labelProvider = (ILabelProvider) commonViewer.getLabelProvider();
				Object[] rootElements = contentProvider.getElements(input);
				calculateElementFilter(rootElements, contentProvider, labelProvider, subMonitor);

				Display.getDefault().asyncExec(() -> {
					commonViewer.refresh();
					commonViewer.expandAll(true);
				});
			} finally {
				Display.getDefault().asyncExec(() -> helper.setBusyImageVisible(commonViewer, false));
			}
		});

		job.setPriority(Job.INTERACTIVE);
		job.setUser(true);
		return job;
	}

	private Set<String> getAllowedClassesForViewer(CommonViewer viewer) {
		INavigatorViewerDescriptor descriptor = viewer.getNavigatorContentService().getViewerDescriptor();

		String allowedClassesString = descriptor
				.getStringConfigProperty("org.eclipse.ui.navigator.searchFilterAllowedClasses"); //$NON-NLS-1$
		if (allowedClassesString == null) {
			allowedClassesString = DEFAULT_ALLOWED_CLASSES;
		}
		if (allowedClassesString.equals(ALL_CLASSES_ALLOWED)) {
			return Collections.emptySet();
		}

		return new HashSet<String>(Arrays.asList(allowedClassesString.split(","))); //$NON-NLS-1$
	}

	private boolean calculateElementFilter(Object[] elements, ITreeContentProvider contentProvider,
			ILabelProvider labelProvider, SubMonitor monitor) {

		List<Boolean> result = Arrays.asList(elements).parallelStream()
				.map(element -> calculateElementFilter(element, contentProvider, labelProvider, monitor))
				.collect(Collectors.toList());
		return result.contains(Boolean.TRUE);
	}

	private boolean calculateElementFilter(Object element, ITreeContentProvider contentProvider,
			ILabelProvider labelProvider, SubMonitor monitor) {

		monitor.checkCanceled();

		if (!isElementAllowed(element)) {
			elementCache.put(element, Boolean.FALSE);
			return false;
		}

		Object[] children = contentProvider.getChildren(element);
		boolean anyChildVisible = calculateElementFilter(children, contentProvider, labelProvider, monitor);

		if (anyChildVisible || isLeafVisible(element, labelProvider)) {
			elementCache.put(element, Boolean.TRUE);
			return true;
		}

		elementCache.put(element, Boolean.FALSE);
		return false;
	}

	private boolean isLeafVisible(Object element, ILabelProvider labelProvider) {
		String label = labelProvider.getText(element);
		return searchPattern.matcher(label).find();
	}

	public void setSearchString(String searchString) {
		if (elementCachePopulationJob.getState() != Job.NONE) {
			if (!elementCachePopulationJob.cancel()) {
				try {
					elementCachePopulationJob.join();
				} catch (InterruptedException e) {
					return;
				}
			}
		}

		elementCache.clear();

		if (searchString == null || searchString.isEmpty()) {
			searchPattern = null;
		} else {
			try {
				searchPattern = Pattern.compile(searchString, Pattern.CASE_INSENSITIVE);
			} catch (PatternSyntaxException e) {
				searchPattern = null;
			}
		}

		if (searchPattern != null) {
			elementCachePopulationJob.schedule();
		} else {
			commonViewer.refresh();
		}
	}

	private boolean isElementAllowed(Object element) {
		if (allowedClasses.isEmpty()) {
			return true;
		}

		Class<? extends Object> elementClass = element.getClass();
		boolean allowed = allowedClassesCache.computeIfAbsent(elementClass, cls -> computeClassAllowed(cls));
		return allowed;
	}

	private boolean computeClassAllowed(Class<?> cls) {
		String name = cls.getCanonicalName();
		if (name != null && allowedClasses.contains(name)) {
			return true;
		}

		Class<?>[] ifcs = cls.getInterfaces();
		if (ifcs != null && Arrays.asList(ifcs).stream().anyMatch(ifc -> computeClassAllowed(ifc))) {
			return true;
		}

		Class<?> parent = cls.getSuperclass();
		if (parent != null) {
			return computeClassAllowed(parent);
		}

		return false;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Assert.isTrue(viewer == commonViewer);

		if (searchPattern == null) {
			return true;
		}

		if (!elementCache.containsKey(element)) {
			ITreeContentProvider contentProvider = (ITreeContentProvider) commonViewer.getContentProvider();
			ILabelProvider labelProvider = (ILabelProvider) commonViewer.getLabelProvider();
			calculateElementFilter(element, contentProvider, labelProvider, SubMonitor.convert(null));
		}

		return elementCache.get(element);
	}

	public void dispose() {
		elementCachePopulationJob.cancel();
	}
}
