                TextCellEditor.this.focusLost();
            }
        });
        text.setFont(parent.getFont());
        text.setBackground(parent.getBackground());
        text.addModifyListener(getModifyListener());
        return text;
    }

    /**
     * The <code>TextCellEditor</code> implementation of
     * this <code>CellEditor</code> framework method returns
     * the text string.
     *
     * @return the text string
     */
    @Override
