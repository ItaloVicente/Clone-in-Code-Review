			}

			this.currentTheme = theme;
			for (CSSEngine engine : cssEngines) {
				engine.reset();
			}
