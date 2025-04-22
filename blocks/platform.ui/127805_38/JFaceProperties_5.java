
package org.eclipse.jface.databinding.swt.typed;

import java.util.Date;

import org.eclipse.jface.databinding.swt.IWidgetListProperty;
import org.eclipse.jface.databinding.swt.IWidgetValueProperty;
import org.eclipse.jface.internal.databinding.swt.ButtonSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.CComboSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.ComboSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.ControlBackgroundProperty;
import org.eclipse.jface.internal.databinding.swt.ControlBoundsProperty;
import org.eclipse.jface.internal.databinding.swt.ControlFocusedProperty;
import org.eclipse.jface.internal.databinding.swt.ControlFontProperty;
import org.eclipse.jface.internal.databinding.swt.ControlForegroundProperty;
import org.eclipse.jface.internal.databinding.swt.ControlLocationProperty;
import org.eclipse.jface.internal.databinding.swt.ControlSizeProperty;
import org.eclipse.jface.internal.databinding.swt.ControlVisibleProperty;
import org.eclipse.jface.internal.databinding.swt.DateTimeSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.ListSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.MenuItemSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.ScaleSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.SliderSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.SpinnerSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetEditableProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetEnabledProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetImageProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetItemsProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetMaximumProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetMessageProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetMinimumProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetSelectionProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetSingleSelectionIndexProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetTextProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetTextWithEventsProperty;
import org.eclipse.jface.internal.databinding.swt.WidgetTooltipTextProperty;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.TrayItem;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.Widget;

public class WidgetProperties {
	public static <S extends Control> IWidgetValueProperty<S, Color> background() {
		return new ControlBackgroundProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Rectangle> bounds() {
		return new ControlBoundsProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Boolean> editable() {
		return new WidgetEditableProperty<>();
	}

	public static <S extends Widget> IWidgetValueProperty<S, Boolean> enabled() {
		return new WidgetEnabledProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Boolean> focused() {
		return new ControlFocusedProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Font> font() {
		return new ControlFontProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Color> foreground() {
		return new ControlForegroundProperty<>();
	}

	public static <S extends Widget> IWidgetValueProperty<S, Image> image() {
		return new WidgetImageProperty<>();
	}

	public static <S extends Control> IWidgetListProperty<S, String> items() {
		return new WidgetItemsProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Point> location() {
		return new ControlLocationProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Integer> maximum() {
		return new WidgetMaximumProperty<>();
	}

	public static <S extends Widget> IWidgetValueProperty<S, String> message() {
		return new WidgetMessageProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Integer> minimum() {
		return new WidgetMinimumProperty<>();
	}

	public static <S extends Widget, T> IWidgetValueProperty<S, T> selection() {
		return new WidgetSelectionProperty<>();
	}

	public static IWidgetValueProperty<DateTime, Date> dateTimeSelection() {
		return new DateTimeSelectionProperty();
	}

	public static IWidgetValueProperty<Button, Boolean> buttonSelection() {
		return new ButtonSelectionProperty();
	}

	public static IWidgetValueProperty<Combo, String> comboSelection() {
		return new ComboSelectionProperty();
	}

	public static IWidgetValueProperty<CCombo, String> ccomboSelection() {
		return new CComboSelectionProperty();
	}

	public static IWidgetValueProperty<List, String> listSelection() {
		return new ListSelectionProperty();
	}

	public static IWidgetValueProperty<MenuItem, Boolean> menuItemSelection() {
		return new MenuItemSelectionProperty();
	}

	public static IWidgetValueProperty<Scale, Integer> scaleSelection() {
		return new ScaleSelectionProperty();
	}

	public static IWidgetValueProperty<Slider, Integer> sliderSelection() {
		return new SliderSelectionProperty();
	}

	public static IWidgetValueProperty<Spinner, Integer> spinnerSelection() {
		return new SpinnerSelectionProperty();
	}

	public static <S extends Control> IWidgetValueProperty<S, Integer> singleSelectionIndex() {
		return new WidgetSingleSelectionIndexProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Point> size() {
		return new ControlSizeProperty<>();
	}

	public static <S extends Widget> IWidgetValueProperty<S, String> text() {
		return new WidgetTextProperty<>();
	}

	public static <S extends Widget> IWidgetValueProperty<S, String> text(final int event) {
		return text(new int[] { event });
	}

	public static <S extends Widget> IWidgetValueProperty<S, String> text(int... events) {
		return new WidgetTextWithEventsProperty<>(events.clone());
	}

	public static <S extends Widget> IWidgetValueProperty<S, String> tooltipText() {
		return new WidgetTooltipTextProperty<>();
	}

	public static <S extends Control> IWidgetValueProperty<S, Boolean> visible() {
		return new ControlVisibleProperty<>();
	}
}
