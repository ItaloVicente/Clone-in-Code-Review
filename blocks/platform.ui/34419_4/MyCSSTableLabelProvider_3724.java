package org.akrogen.tkui.css.swt.engine.table.viewers;

import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.jface.viewers.CSSLabelProvider;
import org.eclipse.jface.viewers.TableViewer;

public class MyCSSLabelProvider extends CSSLabelProvider {

	public MyCSSLabelProvider(CSSEngine engine, TableViewer tableViewer) {
		super(engine, tableViewer);
	}
	
	public String getText(Object element) {		
		return element.toString();
	} 
	
}
