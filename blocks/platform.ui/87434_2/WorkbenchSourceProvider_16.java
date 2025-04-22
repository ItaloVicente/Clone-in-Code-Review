			fireSourceChanged(sourceFlags2, sourceValuesByName2);
			hookListener(lastActiveWorkbenchWindow,
					newActiveWorkbenchWindow);
		}

		if (shellChanged || windowChanged) {
			checkOtherSources((Shell) event.widget);
