        }
        _queryLabel.setText(test.queryText());
    }

    public String getFailureText() {
        return _failureText;
    }

    /*
     * Can't open the verification dialog without a specified
     * test dialog, this simply returns a failure and prevents
     * opening.  Should use open(Dialog) instead.
     *
     */
    @Override
