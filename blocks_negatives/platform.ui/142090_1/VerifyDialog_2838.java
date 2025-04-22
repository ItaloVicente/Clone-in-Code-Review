                e.doit = false;
            }

        });
        _testDialog.open();
    }

    /*
     * The test dialog failed, open the failure dialog.
     */
    private void handleFailure() {
        IDialogTestPass test = _dialogTests[TEST_TYPE];
        StringBuilder text = new StringBuilder();
        String label = test.label();
