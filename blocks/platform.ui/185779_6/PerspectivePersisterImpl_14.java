package org.eclipse.e4.ui.workbench.persistence.compatibility.impl;

import java.io.Reader;
import java.io.StringReader;
import java.util.List;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.internal.persistence.WorkbenchState;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.persistence.PerspectivePersister;
import org.eclipse.e4.ui.workbench.persistence.common.CommonUtil;
import org.eclipse.e4.ui.workbench.persistence.compatibility.PerspectiveMigrator;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.internal.IWorkbenchConstants;

@SuppressWarnings("restriction")
public class PerspectiveMigratorImpl implements PerspectiveMigrator {

	@Override
	public void apply3xWorkbenchState(final String iMemento) throws WorkbenchException {
		MApplication application = convertToMApplication(iMemento);
		WorkbenchState workbenchState = convertToWorkbenchState(application);
		PerspectivePersister.INSTANCE.restoreWorkbenchState(workbenchState);
	}

	@Override
	public MApplication convertToMApplication(final String iMemento) throws WorkbenchException {
		ConverterImpl converter = new ConverterImpl();
		return converter.convert(iMemento);
	}

	@Override
	public WorkbenchState convertToWorkbenchState(final MApplication application) {
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

	@Override
	public boolean isLegacyWorkbench(String iMemento) {
		try {
			Reader reader = new StringReader(iMemento);
			IMemento memento = XMLMemento.createReadRoot(reader);
			return IWorkbenchConstants.TAG_WORKBENCH.equals(memento.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return false;
	}


	public static WorkbenchState createLegacyWorkbenchState(final MPerspective perspective) {
		return CommonUtil.doCreateWorkbenchState(perspective);
	}
}
