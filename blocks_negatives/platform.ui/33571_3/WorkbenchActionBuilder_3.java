                if (event.getProperty().equals(
						IPreferenceConstants.REUSE_EDITORS_BOOLEAN)) {
                    if (window.getShell() != null
                            && !window.getShell().isDisposed()) {
                        window.getShell().getDisplay().syncExec(new Runnable() {
                            @Override
