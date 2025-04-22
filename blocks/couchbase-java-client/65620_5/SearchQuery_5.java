    public SearchQuery highlight(String... fields) {
        return highlight(HighlightStyle.SERVER_DEFAULT, fields);
    }

    public SearchQuery highlight() {
        return highlight(HighlightStyle.SERVER_DEFAULT);
    }

