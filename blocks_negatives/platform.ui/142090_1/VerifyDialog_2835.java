                enable = false;
            }
        }
        _yesButton.setEnabled(enable);
    }

    /*
     * Initializes the checklist, banner texts, and query label
     */
    private void initializeTest() {
        IDialogTestPass test = _dialogTests[TEST_TYPE];
        setTitle(test.title());
        setMessage(test.description());
