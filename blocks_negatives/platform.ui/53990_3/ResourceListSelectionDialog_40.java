            display.syncExec(new Runnable() {
                @Override
				public void run() {
                    if (resourceNames.isDisposed()) {
                        disposed[0] = true;
                        return;
                    }
                    itemCount[0] = resourceNames.getItemCount();
                }
            });
