	            relativePathVariableGroup.setSelection(pathVariableSelected);

	            pathVariable = settings.get(STORE_PATH_VARIABLE_NAME_ID);
	            if (pathVariable != null)
	            	relativePathVariableGroup.selectVariable(pathVariable);
            }
        	updateWidgetEnablements();
        }
    }

    /**
     * 	Since Finish was pressed, write widget values to the dialog store so that they
     *	will persist into the next invocation of this wizard page
     */
    @Override
