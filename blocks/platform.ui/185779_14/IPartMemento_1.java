package org.eclipse.e4.ui.workbench.compatibiliy.migration;

import java.util.List;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.internal.persistence.IWorkbenchState;
import org.eclipse.e4.ui.workbench.internal.persistence.impl.WorkbenchState;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.persistence.PerspectivePersister;
import org.eclipse.e4.ui.workbench.persistence.common.CommonUtil;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.internal.IWorkbenchConstants;

@SuppressWarnings("restriction")
public final class PerspectiveMigrator{

	private PerspectiveMigrator() {}
	
	public static void apply3xWorkbenchState(final IMemento iMemento) {
		MApplication application = convertToMApplication(iMemento);
		IWorkbenchState workbenchState = convertToWorkbenchState(application);
		PerspectivePersister.restoreWorkbenchState(workbenchState);
	}

	public static MApplication convertToMApplication(final IMemento iMemento) {
		Converter converter = new Converter();
		return converter.convert(iMemento);
	}

	public static IWorkbenchState convertToWorkbenchState(final MApplication application) {
		EModelService modelService = CommonUtil.getEModelService();
		MPerspective activePerspective = modelService.getActivePerspective(CommonUtil.getCurrentMainWindow());
		List<MPerspective> findElements = modelService.findElements(application, null, MPerspective.class, null);

		MPerspective mPerspective = null;
		String elementId = activePerspective.getElementId();
		for (MPerspective perspective : findElements) {
			if (elementId.equals(perspective.getElementId())) {
				mPerspective = perspective;
			}
		}

		if (mPerspective == null) {
			System.err
					.println("No perspective with id " + elementId + " was found. Using first perspective to restore"); //$NON-NLS-1$ //$NON-NLS-2$
			mPerspective = findElements.get(0);
			mPerspective.setElementId(elementId);
		}

		return createLegacyWorkbenchState(mPerspective);
	}
	public static boolean isLegacyWorkbench(IMemento iMemento) {
		try {
			return IWorkbenchConstants.TAG_WORKBENCH.equals(iMemento.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}

	public static IWorkbenchState createLegacyWorkbenchState(final MPerspective perspective) {
		return CommonUtil.doCreateWorkbenchState(perspective);
	}
}
