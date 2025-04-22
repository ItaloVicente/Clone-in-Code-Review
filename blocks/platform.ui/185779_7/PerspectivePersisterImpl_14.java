package org.eclipse.e4.ui.workbench.persistence.common;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MSnippetContainer;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MArea;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.model.application.ui.advanced.MPlaceholder;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.internal.persistence.PartMemento;
import org.eclipse.e4.ui.workbench.internal.persistence.PersistenceFactory;
import org.eclipse.e4.ui.workbench.internal.persistence.WorkbenchState;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.XMLMemento;
import org.eclipse.ui.internal.EditorReference;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityEditor;
import org.eclipse.ui.internal.e4.compatibility.CompatibilityView;

@SuppressWarnings({ "restriction" })
public final class CommonUtil {

	private CommonUtil() {
	}

	public static String MEMENTO_KEY = "memento"; //$NON-NLS-1$
	public static String EDITOR_AREA = "org.eclipse.ui.editorss"; //$NON-NLS-1$

	public static IEclipseContext getEclipseContext() {
		return Workbench.getInstance().getContext();
	}

	public static EModelService getEModelService() {
		return getEclipseContext().get(EModelService.class);
	}



	public static MWindow getCurrentMainWindow() {
		MApplication mApplication = getEclipseContext().get(MApplication.class);
		List<MWindow> children = mApplication.getChildren();
		MWindow mWindow = children.get(0);
		return mWindow;
	}

	public static WorkbenchState doCreateWorkbenchState(final MPerspective perspective) {
		MSnippetContainer snippetContainer = createSnippetContainer();
		persistCompatibilityEditors(perspective);
		CommonUtil.getEModelService().cloneElement(perspective, snippetContainer);
		WorkbenchState workbenchState = PersistenceFactory.eINSTANCE.createWorkbenchState();
		workbenchState.setPerspective((MPerspective) snippetContainer.getSnippets().get(0));
		List<MPart> parts = getPerspectivePartsWithState(perspective);
		for (MPart part : parts) {
			PartMemento partMemento = PersistenceFactory.eINSTANCE.createPartMemento();
			partMemento.setPartId(part.getElementId());
			partMemento.setMemento(part.getPersistedState().get(MEMENTO_KEY));
			workbenchState.getViewSettings().add(partMemento);
		}

		MPlaceholder editorAreaPlaceholder = (MPlaceholder) CommonUtil.getEModelService().find(EDITOR_AREA, perspective);
		if (editorAreaPlaceholder != null) {
			MArea editorArea = (MArea) editorAreaPlaceholder.getRef();
			persistCompatibilityEditors(editorArea);
			MArea clonedEditorArea = (MArea) CommonUtil.getEModelService().cloneElement(editorArea, null);
			workbenchState.setEditorArea(clonedEditorArea);
		}

		return workbenchState;
	}

	private static void persistCompatibilityEditors(MUIElement root) {
		CommonUtil.getEModelService().findElements(root, CompatibilityEditor.MODEL_ELEMENT_ID, MPart.class)
				.forEach(CommonUtil::persistCompatibilityEditor);
	}

	private static void persistCompatibilityEditor(MPart part) {
		if (part.getObject() instanceof CompatibilityEditor) {
			CompatibilityEditor editor = (CompatibilityEditor) part.getObject();
			EditorReference reference = (EditorReference) editor.getReference();
			try {
				Method persistMethod = reference.getClass().getDeclaredMethod("persist"); //$NON-NLS-1$
				persistMethod.setAccessible(true);
				persistMethod.invoke(reference);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new IllegalStateException("Cannot call EditorReference's persist method", e); //$NON-NLS-1$
			}
		}
	}

	private static List<MPart> getPerspectivePartsWithState(final MPerspective perspective) {
		List<MPlaceholder> phList = CommonUtil.getEModelService().findElements(perspective, null, MPlaceholder.class, null);
		List<MPart> result = new ArrayList<MPart>();
		for (MPlaceholder ph : phList) {
			MUIElement element = ph.getRef();
			if (!MPart.class.isInstance(element)) {
				continue;
			}
			MPart part = MPart.class.cast(element);

			persist(part);
			result.add(part);
		}
		return result;
	}

	private static void persist(final MPart part) {
		Optional<IViewPart> viewPart = getOptionalViewPart(part);
		if (viewPart.isPresent()) {
			XMLMemento root = XMLMemento.createWriteRoot("view"); //$NON-NLS-1$
			viewPart.get().saveState(root);
			StringWriter writer = new StringWriter();
			try {
				root.save(writer);
				part.getPersistedState().put(MEMENTO_KEY, writer.toString());
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
	}

	public static Optional<IViewPart> getOptionalViewPart(final MPart part) {
		if (!CompatibilityView.class.isInstance(part.getObject())) {
			return Optional.empty();
		}
		CompatibilityView cv = CompatibilityView.class.cast(part.getObject());
		if (cv == null) {
			return Optional.empty();
		}
		IViewPart viewPart = cv.getView();
		return Optional.ofNullable(viewPart);
	}

	public static MSnippetContainer createSnippetContainer() {
		return new MSnippetContainer() {

			private final List<MUIElement> list = new ArrayList<>();

			@Override
			public List<MUIElement> getSnippets() {
				return list;
			}
		};
	}
}
