						long /*int*/ screen = OS.gdk_screen_get_default();
						long /*int*/ provider = OS.gtk_css_provider_new();
						if (screen != 0 && provider != 0) {
							Color color;
							if (isDark) {
								color = display.getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
							} else {
								color = display.getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
							}
							String css = "GtkMenuItem {color: rgb(" + color.getRed() + ", " + 
									color.getGreen() + ", " + color.getBlue() + ");}";
							OS.gtk_style_context_add_provider_for_screen (screen, provider, OS.GTK_STYLE_PROVIDER_PRIORITY_APPLICATION);
							OS.gtk_css_provider_load_from_data (provider, Converter.wcsToMbcs (null, css, true), -1, null);
						}
