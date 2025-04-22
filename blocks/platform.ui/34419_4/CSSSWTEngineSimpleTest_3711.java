package org.akrogen.tkui.css.core.examples.csseditors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

import org.akrogen.tkui.css.core.dom.IElementProvider;
import org.akrogen.tkui.css.core.engine.CSSEngine;
import org.akrogen.tkui.css.core.engine.CSSErrorHandler;
import org.akrogen.tkui.css.core.serializers.CSSHTMLSerializerConfiguration;
import org.akrogen.tkui.css.core.serializers.CSSSerializer;
import org.akrogen.tkui.css.core.serializers.CSSSerializerConfiguration;

public abstract class AbstractCSSEditor {

	protected CSSEngine engine;

	protected CSSSerializer serializer;

	protected String nativeWidgetDir;

	protected java.util.List cssFiles = new ArrayList();

	protected AbstractCSSEditor(String nativeWidgetDir) {
		this.nativeWidgetDir = nativeWidgetDir;
	}

	protected CSSEngine getCSSEngine() {
		if (engine == null) {
			engine = createCSSEngine();
			engine.setErrorHandler(new CSSErrorHandler() {
				public void error(Exception e) {
					handleExceptions(e);
				}
			});

		} else {
			engine.reset();
		}

		if (isHTMLSelector()) {
			engine.setElementProvider(getHTMLElementProvider());
		} else {
			engine.setElementProvider(getNativeWidgetElementProvider());
		}
		return engine;
	}

	protected void applyStyles(Object widget) {
		try {
			Date d1 = new Date();
			engine = getCSSEngine();
			StringReader reader = new StringReader(getStyleSheetContent());
			engine.parseStyleSheet(reader);

			engine.applyStyles(widget, true, true);
			Date d2 = new Date();

			setCSSEngineStatuts("Apply style with "
					+ (d2.getTime() - d1.getTime()) + "ms.");

		} catch (Exception ex) {
			handleExceptions(ex);
		}
	}

	protected void applyStyles() {
		if (mustApplyStylesToWindow()) {
			applyStyles(getWindowNativeWidget());
		} else
			applyStyles(getLeftPanelNativeWidget());
	}

	protected void fillTextareaWithStyleSheetContent(File file) {
		try {
			fillTextareaWithStyleSheetContent(new FileInputStream(file));
		} catch (Exception e) {
			handleExceptions(e);
		}
	}

	protected void fillTextareaWithStyleSheetContent(InputStream stream) {
		try {
			StringWriter writer = new StringWriter();
			InputStreamReader streamReader = new InputStreamReader(stream);
			BufferedReader buffer = new BufferedReader(streamReader);
			String line = "";
			boolean b = false;
			while (null != (line = buffer.readLine())) {
				if (b)
					writer.write("\n");
				writer.write(line);
				b = true;
			}
			buffer.close();
			streamReader.close();
			String content = writer.toString();
			setStyleSheetContent(content);
		} catch (Exception e) {
			handleExceptions(e);
		}
	}

	protected void fillTextareaWithDefaultStyleSheetContent() {
		if (mustApplyStylesToWindow())
			fillTextareaWithDefaultStyleSheetContent(getWindowNativeWidget());
		else
			fillTextareaWithDefaultStyleSheetContent(getLeftPanelNativeWidget());
	}

	protected void fillTextareaWithDefaultStyleSheetContent(Object widget) {
		if (serializer == null)
			this.serializer = new CSSSerializer();
		StringWriter writer = new StringWriter();
		try {
			CSSSerializerConfiguration configuration = (isHTMLSelector() ? getCSSHTMLSerializerConfiguration()
					: getCSSNativeWidgetSerializerConfiguration());
			serializer.serialize(writer, getCSSEngine(), widget, true,
					configuration);
			setStyleSheetContent(writer.toString());
		} catch (Exception e) {
			handleExceptions(e);
		}
	}

	protected void applyStylesFromSelectedFile() {
		int index = getCSSFilesWidgetSelectionIndex();
		if (index == -1)
			if (getCSSFilesWidgetItemCount() > 1) {
				index = 1;
				selectCSSFilesWidget(index);
			}
		if (index < 1) {
			setStyleSheetContent("");
			return;
		}
		File file = (File) cssFiles.get(index - 1);
		fillTextareaWithStyleSheetContent(file);
	}

	protected void populateCSSFiles() {
		removeAllCSSFilesWidget();
		int size = cssFiles.size();
		for (int i = 0; i < size; i++) {
			cssFiles.remove(0);
		}
		addItemCSSFilesWidget("None");
		File baseDir = getBaseStyleDir();
		File[] files = baseDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isFile()) {
				addItemCSSFilesWidget(file.getName());
				cssFiles.add(file);
			}
		}
	}

	protected void handleExceptions(Exception e) {
		e.printStackTrace();
	}

	protected File getBaseStyleDir() {
		if (isHTMLSelector())
			return new File(getBaseStyleDirName() + "/html");
		if (nativeWidgetDir != null)
			return new File(getBaseStyleDirName() + "/" + nativeWidgetDir);
		return new File(getBaseStyleDirName());
	}

	protected String getBaseStyleDirName() {
		return "styles";
	}

	protected abstract CSSEngine createCSSEngine();

	protected abstract boolean isHTMLSelector();

	protected abstract IElementProvider getNativeWidgetElementProvider();

	protected abstract IElementProvider getHTMLElementProvider();

	protected abstract String getStyleSheetContent();

	protected abstract void setStyleSheetContent(String content);

	protected abstract void setCSSEngineStatuts(String status);

	protected abstract boolean mustApplyStylesToWindow();

	protected abstract Object getWindowNativeWidget();

	protected abstract Object getLeftPanelNativeWidget();

	protected abstract CSSSerializerConfiguration getCSSNativeWidgetSerializerConfiguration();

	protected CSSSerializerConfiguration getCSSHTMLSerializerConfiguration() {
		return CSSHTMLSerializerConfiguration.INSTANCE;
	}

	protected abstract int getCSSFilesWidgetSelectionIndex();

	protected abstract int getCSSFilesWidgetItemCount();

	protected abstract void selectCSSFilesWidget(int index);

	protected abstract void removeAllCSSFilesWidget();

	protected abstract void addItemCSSFilesWidget(String item);

}
