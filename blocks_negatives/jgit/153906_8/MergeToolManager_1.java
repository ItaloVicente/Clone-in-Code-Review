			/**
			 * @param gui
			 *            use the diff.guitool setting ?
			 * @return the default tool name
			 */
			public String getDefaultToolName(BooleanOption gui) {
				return gui.toBoolean() ? config.getDefaultGuiToolName()
						: config.getDefaultToolName();
			}
