package org.eclipse.e4.demo.cssbridge.ui.views;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.themes.ITheme;

public class Theme {
	public static final String ID = "org.eclipse.e4.demo.cssbridge.ui.views.Theme";

	public interface Shell {
		String BACKGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.shell.background";
		String SELECTION_FOREGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.shell.seletion.foreground";
		String SELECTION_BACKGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.shell.seletion.background";
		String TEXT_AND_LABEL_FOREGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.shell.text.and.label.foreground";
		String LINK_FOREGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.shell.link.foreground";
	}

	public interface FolderPreviewView {
		String LOW_IMP_MAIL_FONT = "org.eclipse.e4.demo.cssbridge.ui.views.theme.folderpreviewview.low.imp.mail.font";
		String LOW_IMP_MAIL_FOREGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.folderpreviewview.low.imp.mail.foreground";

		String NORMAL_IMP_MAIL_FONT = "org.eclipse.e4.demo.cssbridge.ui.views.theme.folderpreviewview.normal.imp.mail.font";
		String NORMAL_IMP_MAIL_FOREGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.folderpreviewview.normal.imp.mail.foreground";

		String HIGH_IMP_MAIL_FONT = "org.eclipse.e4.demo.cssbridge.ui.views.theme.folderpreviewview.high.imp.mail.font";
		String HIGH_IMP_MAIL_FOREGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.folderpreviewview.high.imp.mail.foreground";
	}

	public interface FoldersView {
		String MAILBOX_NAME_FONT = "org.eclipse.e4.demo.cssbridge.ui.views.theme.foldersview.mailbox.name.font";
		String MAILBOX_NAME_FOREGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.foldersview.mailbox.name.foreground";

		String FOLDER_TYPE_FONT = "org.eclipse.e4.demo.cssbridge.ui.views.theme.foldersview.folder.type.font";
		String FOLDER_TYPE_FOREGROUND = "org.eclipse.e4.demo.cssbridge.ui.views.theme.foldersview.folder.type.foreground";
	}

	public static Font getFont(String id) {
		try {
			return getCurrentTheme().getFontRegistry().get(id);

		} catch (NullPointerException exc) {
			return getCurrentTheme().getFontRegistry().get(
					JFaceResources.DEFAULT_FONT);
		}
	}

	public static Color getColor(String id) {
		return getCurrentTheme().getColorRegistry().get(id);
	}

	private static ITheme getCurrentTheme() {
		return PlatformUI.getWorkbench().getThemeManager().getCurrentTheme();
	}
}
