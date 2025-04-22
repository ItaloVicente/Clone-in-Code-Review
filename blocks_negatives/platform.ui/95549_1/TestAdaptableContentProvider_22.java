            ctrl.getDisplay().syncExec(new Runnable() {
                @Override
				public void run() {
                    processDelta(delta);
                }
            });
