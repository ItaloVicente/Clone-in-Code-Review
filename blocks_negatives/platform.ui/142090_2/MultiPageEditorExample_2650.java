        super.pageChange(newPageIndex);
        if (newPageIndex == 2) {
            sortWords();
        }
    }

    /**
     * Sets the font related data to be applied to the text in page 2.
     */
    void setFont() {
        FontDialog fontDialog = new FontDialog(getSite().getShell());
        fontDialog.setFontList(text.getFont().getFontData());
        FontData fontData = fontDialog.open();
        if (fontData != null) {
            if (font != null)
                font.dispose();
            font = new Font(text.getDisplay(), fontData);
            text.setFont(font);
        }
    }

    /**
     * Sorts the words in page 0, and shows them in page 2.
     */
    void sortWords() {

        String editorText = editor.getDocumentProvider().getDocument(
                editor.getEditorInput()).get();

        StringTokenizer tokenizer = new StringTokenizer(editorText,
                " \t\n\r\f!@#$%^&*()-_=+`~[]{};:'\",.<>/?|\\"); //$NON-NLS-1$
        List<String> editorWords = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            editorWords.add(tokenizer.nextToken());
        }

        Collections.sort(editorWords, Collator.getInstance());
        StringWriter displayText = new StringWriter();
        for (int i = 0; i < editorWords.size(); i++) {
            displayText.write((editorWords.get(i)));
        }
        text.setText(displayText.toString());
    }

    @Override
