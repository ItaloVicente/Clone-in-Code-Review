
	private void drawCustomBackground(GC gc, Rectangle bounds, int state) {
		boolean selected = (state & SWT.SELECTED) != 0;
		Color defaultBackground = selected ? parent.getSelectionBackground()
				: parent.getBackground();
		boolean vertical = selected ? parentWrapper
				.isSelectionGradientVertical() : parentWrapper
				.isGradientVertical();
		Rectangle partHeaderBounds = computeTrim(PART_HEADER, state, bounds.x,
				bounds.y, bounds.width, bounds.height);
		
		drawUnselectedTabBackground(gc, partHeaderBounds, state, vertical,
				defaultBackground);
		drawTabBackground(gc, partHeaderBounds, state, vertical,
				defaultBackground);
		drawChildrenBackground(partHeaderBounds);
	}

	private void drawUnselectedTabBackground(GC gc, Rectangle partHeaderBounds,
			int state, boolean vertical, Color defaultBackground) {
		if (unselectedTabsColors == null) {
			boolean selected = (state & SWT.SELECTED) != 0;
			unselectedTabsColors = selected ? parentWrapper
					.getSelectionGradientColors() : parentWrapper
					.getGradientColors();
			unselectedTabsPercents = selected ? parentWrapper
					.getSelectionGradientPercents() :
				parentWrapper.getGradientPercents();
		}
		if (unselectedTabsColors == null) {
			unselectedTabsColors = new Color[] { gc.getDevice().getSystemColor(
					SWT.COLOR_WHITE) };
			unselectedTabsPercents = new int[] { 100 };
		}

		rendererWrapper.drawBackground(gc, partHeaderBounds.x,
				partHeaderBounds.y - 1, partHeaderBounds.width,
				partHeaderBounds.height, defaultBackground,
				unselectedTabsColors, unselectedTabsPercents, vertical);
	}

	private void drawTabBackground(GC gc, Rectangle partHeaderBounds,
			int state, boolean vertical, Color defaultBackground) {
		Color[] colors = selectedTabFillColors;
		int[] percents = selectedTabFillPercents;

		if (colors != null && colors.length == 2) {
			colors = new Color[] { colors[1], colors[0] };
		}
		if (colors == null) {
			boolean selected = (state & SWT.SELECTED) != 0;
			colors = selected ? parentWrapper.getSelectionGradientColors() : 
				parentWrapper.getGradientColors();
			percents = selected ? parentWrapper.getSelectionGradientPercents() : 
				parentWrapper.getGradientPercents();
		}
		if (colors == null) {
			colors = new Color[] { gc.getDevice().getSystemColor(SWT.COLOR_WHITE) };
			percents = new int[] { 100 };
		}
		rendererWrapper.drawBackground(gc, partHeaderBounds.x,  partHeaderBounds.height - 1, partHeaderBounds.width,
				parent.getBounds().height, defaultBackground, colors, percents,
				vertical);
	}

	private void drawChildrenBackground(Rectangle partHeaderBounds) {
		for (Control control : parent.getChildren()) {
			if (control instanceof Composite
					&& !hasBackgroundOverriddenByCSS(control)) {
				drawChildBackground((Composite) control, partHeaderBounds);
			}
		}
	}

	private void drawChildBackground(Composite composite,
			Rectangle partHeaderBounds) {
		Rectangle rec = composite.getBounds();
		Color background = null;
		boolean partOfHeader = rec.y >= partHeaderBounds.y
				&& rec.y < partHeaderBounds.height;

		if (!partOfHeader && selectedTabFillColors != null) {
			background = selectedTabFillColors.length == 2 ? selectedTabFillColors[1]
						: selectedTabFillColors[0];
		}

		setBackgroundOverriddenDuringRenderering(composite, background);
	}

	private static class CTabFolderRendererWrapper extends
			ReflectionSupport<CTabFolderRenderer> {
		private Method drawBackgroundMethod;

		public CTabFolderRendererWrapper(CTabFolderRenderer instance) {
			super(instance);
		}

		public void drawBackground(GC gc, int x, int y, int width, int height,
				Color defaultBackground, Color[] colors, int[] percents,
				boolean vertical) {
			if (drawBackgroundMethod == null) {
				drawBackgroundMethod = getMethod("drawBackground", //$NON-NLS-1$
						new Class<?>[] { GC.class, int[].class, int.class,
								int.class, int.class, int.class, Color.class,
								Image.class, Color[].class, int[].class,
								boolean.class });
			}
			executeMethod(drawBackgroundMethod, new Object[] { gc, null, x, y,
					width, height, defaultBackground, null, colors, percents,
					vertical });
		}
	}

	private static class CTabFolderWrapper extends
			ReflectionSupport<CTabFolder> {
		private Field selectionGradientVerticalField;

		private Field gradientVerticalField;

		private Field selectionGradientColorsField;

		private Field selectionGradientPercentsField;

		private Field gradientColorsField;

		private Field gradientPercentsField;

		public CTabFolderWrapper(CTabFolder instance) {
			super(instance);
		}

		public boolean isSelectionGradientVertical() {
			if (selectionGradientVerticalField == null) {
			}
			Boolean result = (Boolean) getFieldValue(selectionGradientVerticalField);
			return result != null ? result : true;
		}

		public boolean isGradientVertical() {
			if (gradientVerticalField == null) {
			}
			Boolean result = (Boolean) getFieldValue(gradientVerticalField);
			return result != null ? result : true;
		}

		public Color[] getSelectionGradientColors() {
			if (selectionGradientColorsField == null) {
			}
			return (Color[]) getFieldValue(selectionGradientColorsField);
		}

		public int[] getSelectionGradientPercents() {
			if (selectionGradientPercentsField == null) {
			}
			return (int[]) getFieldValue(selectionGradientPercentsField);
		}

		public Color[] getGradientColors() {
			if (gradientColorsField == null) {
			}
			return (Color[]) getFieldValue(gradientColorsField);
		}

		public int[] getGradientPercents() {
			if (gradientPercentsField == null) {
			}
			return (int[]) getFieldValue(gradientPercentsField);
		}
	}

	private static class ReflectionSupport<T> {
		private T instance;

		public ReflectionSupport(T instance) {
			this.instance = instance;
		}

		protected Object getFieldValue(Field field) {
			Object value = null;
			if (field != null) {
				boolean accessible = field.isAccessible();
				try {
					field.setAccessible(true);
					value = field.get(instance);
				} catch (Exception exc) {
				} finally {
					field.setAccessible(accessible);
				}
			}
			return value;
		}

		protected Field getField(String name) {
			Class<?> cls = instance.getClass();
			while (!cls.equals(Object.class)) {
				try {
					return cls.getDeclaredField(name);
				} catch (Exception exc) {
					cls = cls.getSuperclass();
				}
			}
			return null;
		}
		
		protected Object executeMethod(Method method, Object... params) {
			Object value = null;
			if (method != null) {
				boolean accessible = method.isAccessible();
				try {
					method.setAccessible(true);
					value = method.invoke(instance, params);
				} catch (Exception exc) {
				} finally {
					method.setAccessible(accessible);
				}
			}
			return value;
		}

		protected Method getMethod(String name, Class<?>... params) {
			Class<?> cls = instance.getClass();
			while (!cls.equals(Object.class)) {
				try {
					return cls.getDeclaredMethod(name, params);
				} catch (Exception exc) {
					cls = cls.getSuperclass();
				}
			}
			return null;
		}
	}
