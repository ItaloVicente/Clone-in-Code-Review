public class DiffViewer extends SourceViewer {

	private DiffStyleRangeFormatter formatter;

	private DeviceResourceManager colors = new DeviceResourceManager(PlatformUI
			.getWorkbench().getDisplay());

	private LineNumberRulerColumn lineNumberRuler;

	private Color hunkBackgroundColor;

	private Color hunkForegroundColor;
