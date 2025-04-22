			/**
			 * @param gui
			 *            use the diff.guitool setting ?
			 * @return the default tool name
			 */
			public String getDefaultToolName(boolean gui) {
				return gui ? config.getDefaultGuiToolName()
						: config.getDefaultToolName();
			}
