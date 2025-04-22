package org.eclipse.e4.ui.workbench.persistence.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.e4.ui.model.application.MApplicationElement;
import org.eclipse.e4.ui.model.application.ui.SideValue;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimBar;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.Selector;
import org.eclipse.e4.ui.workbench.internal.persistence.IWorkbenchState;
import org.eclipse.e4.ui.workbench.internal.persistence.impl.WorkbenchState;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.persistence.common.CommonUtil;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityEditor;

@SuppressWarnings("restriction")
public final class Util {

	private Util() {
	}

	public static EPartService getEPartService() {
		return CommonUtil.getEclipseContext().get(EPartService.class);
	}

	public static MPerspective getMainPerspectiveFromWindow(final MWindow window, final String perspectiveId) {

		final List<MPerspective> findElements = CommonUtil.getEModelService().findElements(window, MPerspective.class,
				EModelService.ANYWHERE, new Selector() {

					@Override
					public boolean select(final MApplicationElement element) {
						return Objects.equals(element.getElementId(), perspectiveId);
					}
				});

		if (findElements == null || findElements.isEmpty()) {
			throw new IllegalStateException("No perspective found with id " + perspectiveId); //$NON-NLS-1$
		} else if (findElements.size() > 1) {
			throw new IllegalStateException("Too many perspectives found"); //$NON-NLS-1$
		}
		return findElements.get(0);
	}

	public static IWorkbenchState createWorkbenchState(final MWindow window, final MPerspective perspective) {
		if (window == null) {
			throw new NullPointerException("Window must not be null"); //$NON-NLS-1$
		}
		IWorkbenchState workbenchState = CommonUtil.doCreateWorkbenchState(perspective);

		if (window instanceof MTrimmedWindow) {
			MTrimmedWindow tw = (MTrimmedWindow) window;

			tw.getTrimBars().stream().filter(Util::isSideBar)
					.map(t -> (MTrimBar) CommonUtil.getEModelService().cloneElement(t, null))
					.forEach(workbenchState.getTrimBars()::add);
		}

		return workbenchState;
	}

	public static Optional<IEditorPart> getOptionalEditorPart(final MPart part) {
		if (!CompatibilityEditor.class.isInstance(part.getObject())) {
			return Optional.empty();
		}
		CompatibilityEditor ce = CompatibilityEditor.class.cast(part.getObject());
		if (ce == null) {
			return Optional.empty();
		}
		IEditorPart editorPart = ce.getEditor();
		return Optional.ofNullable(editorPart);
	}

	public static boolean isSideBar(MTrimBar trimBar) {
		return trimBar.getSide() == SideValue.LEFT || trimBar.getSide() == SideValue.RIGHT;
	}
}
