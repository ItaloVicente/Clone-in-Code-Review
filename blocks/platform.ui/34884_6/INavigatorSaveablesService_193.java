
package org.eclipse.ui.navigator;

public interface INavigatorPipelineService {

	public PipelinedShapeModification interceptAdd(
			PipelinedShapeModification anAddModification);

	public PipelinedShapeModification interceptRemove(
			PipelinedShapeModification aRemoveModification);
	
	boolean interceptRefresh(PipelinedViewerUpdate aRefreshSynchronization);
	
	public boolean interceptUpdate(
			PipelinedViewerUpdate anUpdateSynchronization);

}
