            d.asyncExec(new Runnable() {
                @Override
				public void run() {
                    asyncDrop(event, page);
                }
            });
