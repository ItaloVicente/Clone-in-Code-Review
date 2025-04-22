        BusyIndicator.showWhile(control.getDisplay(), new Runnable() {
            @Override
			public void run() {
                try {
                    control.setRedraw(false);
                    introPart.standbyStateChanged(standby);
                } finally {
                    control.setRedraw(true);
                }

                setBarVisibility(standby);
            }
        });
