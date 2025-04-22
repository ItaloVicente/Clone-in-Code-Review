						return null;
					}
				};
			} else {
				overrides = parent.getOverrides();
			}
			super.setOverrides(overrides);
		}
		return overrides;
	}

	public IContributionManager getParent() {
		return parent;
	}

	@Override
