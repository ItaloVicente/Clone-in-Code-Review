					if (getParent() != null) {
						overrides = getParent().getOverrides();
					}

					if (overrides != null) {
						text = getParent().getOverrides().getText(this);
					}
