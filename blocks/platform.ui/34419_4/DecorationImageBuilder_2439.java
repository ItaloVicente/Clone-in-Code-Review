package org.eclipse.ui.internal.decorators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationContext;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.IDecorationContext;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.internal.WorkbenchPlugin;

public class DecorationBuilder implements IDecoration {

	private static int DECORATOR_ARRAY_SIZE = 6;

	private List prefixes = new ArrayList();

	private List suffixes = new ArrayList();

	private ImageDescriptor[] descriptors = new ImageDescriptor[DECORATOR_ARRAY_SIZE];

	private Color foregroundColor;

	private Color backgroundColor;

	private Font font;

	LightweightDecoratorDefinition currentDefinition;

	private boolean valueSet = false;

	private final IDecorationContext context;

	DecorationBuilder() {
		this(DecorationContext.DEFAULT_CONTEXT);
	}

	public DecorationBuilder(IDecorationContext context) {
		this.context = context;
	}

	void setCurrentDefinition(LightweightDecoratorDefinition definition) {
		this.currentDefinition = definition;
	}

	@Override
	public void addOverlay(ImageDescriptor overlay) {
		int quadrant = currentDefinition.getQuadrant();
		if (descriptors[quadrant] == null) {
			descriptors[quadrant] = overlay;
		}
		valueSet = true;
	}

	@Override
	public void addOverlay(ImageDescriptor overlay, int quadrant) {
		if (quadrant >= 0 && quadrant < DECORATOR_ARRAY_SIZE) {
			if (descriptors[quadrant] == null) {
				descriptors[quadrant] = overlay;
			}
			valueSet = true;
		} else {
			WorkbenchPlugin
					.log("Unable to apply decoration for " + currentDefinition.getId() + " invalid quadrant: " + quadrant); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public void addPrefix(String prefixString) {
		prefixes.add(prefixString);
		valueSet = true;
	}

	@Override
	public void addSuffix(String suffixString) {
		suffixes.add(suffixString);
		valueSet = true;
	}

	DecorationResult createResult() {
		boolean clearReplacementImage = true;
		if (context != null) {
			Object propertyValue = context.getProperty(IDecoration.ENABLE_REPLACE);
			if (propertyValue instanceof Boolean) {
				if (((Boolean) propertyValue).booleanValue()) {
					clearReplacementImage = false;
				}
			}
		}
		if (clearReplacementImage) {
			descriptors[IDecoration.REPLACE] = null;
		}
		DecorationResult newResult = new DecorationResult(new ArrayList(
				prefixes), new ArrayList(suffixes), descriptors,
				foregroundColor, backgroundColor, font);

		return newResult;
	}

	void clearContents() {
		this.prefixes.clear();
		this.suffixes.clear();
		this.descriptors = new ImageDescriptor[DECORATOR_ARRAY_SIZE];
		valueSet = false;
	}

	boolean hasValue() {
		return valueSet;
	}

	void applyResult(DecorationResult result) {
		prefixes.addAll(result.getPrefixes());
		suffixes.addAll(result.getSuffixes());
		ImageDescriptor[] resultDescriptors = result.getDescriptors();
		if (resultDescriptors != null) {
			for (int i = 0; i < descriptors.length; i++) {
				if (resultDescriptors[i] != null) {
					descriptors[i] = resultDescriptors[i];
				}
			}
		}

		setForegroundColor(result.getForegroundColor());
		setBackgroundColor(result.getBackgroundColor());
		setFont(result.getFont());
		valueSet = true;
	}


	@Override
	public void setBackgroundColor(Color bgColor) {
		this.backgroundColor = bgColor;
		valueSet = true;
	}

	@Override
	public void setFont(Font newFont) {
		this.font = newFont;
		valueSet = true;
	}

	@Override
	public void setForegroundColor(Color fgColor) {
		this.foregroundColor = fgColor;
		valueSet = true;
	}

	@Override
	public IDecorationContext getDecorationContext() {
		return context;
	}
}
