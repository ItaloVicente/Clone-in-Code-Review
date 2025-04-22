				relativePathVariableGroup.setSelection(pathVariableSelected);

				pathVariable = settings.get(STORE_PATH_VARIABLE_NAME_ID);
				if (pathVariable != null)
					relativePathVariableGroup.selectVariable(pathVariable);
			}
			updateWidgetEnablements();
		}
	}

	@Override
