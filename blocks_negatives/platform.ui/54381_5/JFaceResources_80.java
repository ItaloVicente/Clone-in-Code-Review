			toQuery.disposeExec(new Runnable() {
				@Override
				public void run() {
					mgr.dispose();
					registries.remove(toQuery);
				}
