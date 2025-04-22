								getColorRegistry().put(colorDefinition.getId(), rgb);
								processDefaultsTo(colorDefinition.getId(), rgb);
							}
						}
					}
				}
			};
		}
		return propertyListener;
	}

	private IPropertyChangeListener getCascadeListener() {
		if (themeListener == null) {
			themeListener = event -> firePropertyChange(event);
		}
		return themeListener;
	}

	@Override
