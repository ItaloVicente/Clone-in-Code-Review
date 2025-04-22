        browser.addCloseWindowListener(new CloseWindowListener() {
            @Override
			public void close(WindowEvent event) {
                if (newWindow)
                    getShell().dispose();
                else
                    container.close();
            }
        });
