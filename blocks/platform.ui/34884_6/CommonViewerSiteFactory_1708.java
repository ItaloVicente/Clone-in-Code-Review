package org.eclipse.ui.navigator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.internal.navigator.CommonNavigatorFrameSource;
import org.eclipse.ui.internal.navigator.ContributorTrackingSet;
import org.eclipse.ui.internal.navigator.NavigatorContentService;
import org.eclipse.ui.internal.navigator.NavigatorDecoratingLabelProvider;
import org.eclipse.ui.internal.navigator.NavigatorPipelineService;
import org.eclipse.ui.internal.navigator.dnd.NavigatorDnDService;
import org.eclipse.ui.internal.navigator.framelist.FrameList;

public class CommonViewer extends TreeViewer {

	private final NavigatorContentService contentService;

	private ISelection cachedSelection;
	
	private FrameList frameList;
	
	private CommonNavigator commonNavigator;

	private ICommonViewerMapper _mapper;
	
	public CommonViewer(String aViewerId, Composite aParent, int aStyle) {
		super(aParent, aStyle);
		contentService = new NavigatorContentService(aViewerId, this);
		init();
	}

	protected void init() {
		setUseHashlookup(true);
		setContentProvider(contentService.createCommonContentProvider());
		setLabelProvider(new NavigatorDecoratingLabelProvider(contentService.createCommonLabelProvider()));
		initDragAndDrop();
	}

	void setCommonNavigator(CommonNavigator navigator) {
		commonNavigator = navigator;
	}

	public void setMapper(ICommonViewerMapper mapper) {
		_mapper = mapper;
	}
	
	public ICommonViewerMapper getMapper() {
		return _mapper;
	}
	
	public CommonNavigator getCommonNavigator() {
		return commonNavigator;
	}
	
	protected void removeWithoutRefresh(Object[] elements) {
		super.remove(elements);
	}

	protected void initDragAndDrop() {

		int operations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;

		CommonDragAdapter dragAdapter = createDragAdapter();
		addDragSupport(operations, dragAdapter.getSupportedDragTransfers(),
				dragAdapter);
		
		CommonDropAdapter dropAdapter = createDropAdapter();
		addDropSupport(operations, dropAdapter.getSupportedDropTransfers(),
				dropAdapter);

		NavigatorDnDService dnd = (NavigatorDnDService)contentService.getDnDService();
		dnd.setDropAdaptor(dropAdapter);
	}

	
	protected CommonDragAdapter createDragAdapter() {
		return new CommonDragAdapter(contentService, this);
	}
	
	
	protected CommonDropAdapter createDropAdapter() {
		return new CommonDropAdapter(contentService, this);
	}
	
	
	@Override
	protected void handleLabelProviderChanged(LabelProviderChangedEvent event) {

		Object[] changed = event.getElements();
		if (changed != null) {
			List<Object> others = new ArrayList<Object>();
			for (int i = 0; i < changed.length; i++) {
				if (changed[i] == null)
					continue;
				
				if (_mapper != null) {
					if (_mapper.handlesObject(changed[i])) {
						_mapper.objectChanged(changed[i]);
						continue;
					}
				}
				others.add(changed[i]);
			}
			if (others.isEmpty()) {
				return;
			}
			event = new LabelProviderChangedEvent((IBaseLabelProvider) event
					.getSource(), others.toArray());
		}
		super.handleLabelProviderChanged(event);
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		dispose();
		super.handleDispose(event);
	}
 
	public void dispose() {
		if (contentService != null) {
			contentService.dispose();
		}
		clearSelectionCache();
	}

	@Override
	public void setSorter(ViewerSorter sorter) {
		if (sorter != null && sorter instanceof CommonViewerSorter) {
			((CommonViewerSorter) sorter).setContentService(contentService);
		}

		super.setSorter(sorter);
	}

	public INavigatorContentService getNavigatorContentService() {
		return contentService;
	}

	@Override
	public void add(Object parentElement, Object[] childElements) {
		NavigatorPipelineService pipeDream = (NavigatorPipelineService) contentService
				.getPipelineService();

		PipelinedShapeModification modification = new PipelinedShapeModification(
				parentElement, new ContributorTrackingSet(contentService,
						childElements));

		pipeDream.interceptAdd(modification);

		Object parent = (parentElement == getInput()) ? getInput()
				: modification.getParent();

		super.add(parent, modification.getChildren().toArray());
	}

	@Override
	public void remove(Object[] elements) {
		NavigatorPipelineService pipeDream = (NavigatorPipelineService) contentService
				.getPipelineService();

		PipelinedShapeModification modification = new PipelinedShapeModification(
				null, new ContributorTrackingSet(contentService, elements));

		pipeDream.interceptRemove(modification);

		super.remove(modification.getChildren().toArray());
	}

	@Override
	public void refresh(Object element, boolean updateLabels) {

		if(element != getInput()) {
			INavigatorPipelineService pipeDream = contentService
					.getPipelineService();
	
			PipelinedViewerUpdate update = new PipelinedViewerUpdate();
			update.getRefreshTargets().add(element);
			update.setUpdateLabels(updateLabels);
			if (pipeDream.interceptRefresh(update)) {
				boolean toUpdateLabels = update.isUpdateLabels();
				for (Iterator<Object> iter = update.getRefreshTargets().iterator(); iter
						.hasNext();) {
					super.refresh(iter.next(), toUpdateLabels);
				}
			} else {
				super.refresh(element, updateLabels);
			}
		} else {
			super.refresh(element, updateLabels);
		}
	}
	
	@Override
	public void setSelection(ISelection selection, boolean reveal) { 

		if(selection instanceof IStructuredSelection) {
			IStructuredSelection sSelection = (IStructuredSelection) selection;
			
			INavigatorPipelineService pipeDream = contentService
					.getPipelineService();

			PipelinedViewerUpdate update = new PipelinedViewerUpdate();
			update.getRefreshTargets().addAll(sSelection.toList());
			update.setUpdateLabels(false);
			if (pipeDream.interceptRefresh(update)) {
				super.setSelection(new StructuredSelection(update.getRefreshTargets().toArray()) , reveal);
			} else {
				super.setSelection(selection, reveal);
			}
		}
	}
	
    @Override
	protected void hookControl(Control control) {
    	super.hookControl(control);
        control.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
            	clearSelectionCache();
            }
        });
    }


	public void doUpdateItem(Widget item) {
		doUpdateItem(item, item.getData(), true);
	}

	@Override
	protected void mapElement(Object element, Widget item) {
		super.mapElement(element, item);
		if (_mapper != null && item instanceof Item) {
			_mapper.addToMap(element, (Item) item);
		}
	}

	@Override
	protected void unmapElement(Object element, Widget item) {
		if (_mapper != null && item instanceof Item) {
			_mapper.removeFromMap(element, (Item) item);
		}
		super.unmapElement(element, item);
	}

	@Override
	protected void unmapAllElements() {
		if (_mapper != null)
			_mapper.clearMap();
		super.unmapAllElements();
	}

	@Override
	protected void setSelectionToWidget(List v, boolean reveal) {
		clearSelectionCache();
		super.setSelectionToWidget(v, reveal);
	}
	
	@Override
	protected void handleDoubleSelect(SelectionEvent event) {
		clearSelectionCache();
		super.handleDoubleSelect(event);
	}
	
	@Override
	protected void handleOpen(SelectionEvent event) {
		clearSelectionCache();
		super.handleOpen(event);
	}
	
	@Override
	protected void handlePostSelect(SelectionEvent e) {
		clearSelectionCache();
		super.handlePostSelect(e);
	}
	
	@Override
	protected void handleSelect(SelectionEvent event) {
		clearSelectionCache();
		super.handleSelect(event);
	}
	
	private void clearSelectionCache() {
		cachedSelection = null;
	}
	
	@Override
	public ISelection getSelection() {
		if (cachedSelection == null) {
			cachedSelection = super.getSelection();
		}
		return cachedSelection;
	}

	@Override
	public void refresh(Object element) {
		refresh(element, true);
	}

	@Override
	public void update(Object element, String[] properties) {
		if(element != getInput()) {
			INavigatorPipelineService pipeDream = contentService
					.getPipelineService();
	
			PipelinedViewerUpdate update = new PipelinedViewerUpdate();
			update.getRefreshTargets().add(element);
			update.setUpdateLabels(true);
			if (pipeDream.interceptUpdate(update)) {
				for (Iterator<Object> iter = update.getRefreshTargets().iterator(); iter
						.hasNext();) {
					super.update(iter.next(), properties);
				}
			} else {
				super.update(element, properties);
			}
		} else {
			super.update(element, properties);
		}
	}

	@Override
	public String toString() {
		return contentService.toString() + " Viewer"; //$NON-NLS-1$
	}

	@Override
	protected void internalRefresh(Object element, boolean updateLabels) {
		if (element == null && getRoot() == null) {
			return;
		}
		super.internalRefresh(element, updateLabels);
	}

    public void createFrameList() {
        CommonNavigatorFrameSource frameSource = new CommonNavigatorFrameSource(commonNavigator);
        frameList = new FrameList(frameSource);
        frameSource.connectTo(frameList);
    }
    
    public FrameList getFrameList() {
        return frameList;
    }
	
	
}
