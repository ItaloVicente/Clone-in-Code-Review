		if (fillSearch)
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				@Override
				public void run() {
					if (!getControl().isDisposed())
						doSearch();
