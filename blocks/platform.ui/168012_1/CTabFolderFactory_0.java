package org.eclipse.jface.widgets;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public final class CTabFolderFactory extends AbstractControlFactory<CTabFolderFactory, CTabFolder> {
	List<WidgetSupplier<CTabItem, CTabFolder>> tabItems = new ArrayList<>();

	private CTabFolderFactory(int style) {
		super(CTabFolderFactory.class, (Composite parent) -> new CTabFolder(parent, style));
	}

	public static CTabFolderFactory newCTabFolder(int style) {
		return new CTabFolderFactory(style);
	}

	public CTabFolderFactory onSelect(Consumer<SelectionEvent> consumer) {
		addProperty(c -> c.addSelectionListener(SelectionListener.widgetSelectedAdapter(consumer)));
		return this;
	}


}
