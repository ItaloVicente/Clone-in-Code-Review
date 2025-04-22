        MessagePage page = new MessagePage();
        initPage(page);
        page.createControl(book);
        page.setMessage(defaultText);
        return page;
    }

    /**
     * The <code>PageBookView</code> implementation of this <code>IWorkbenchPart</code>
     * method creates a <code>PageBook</code> control with its default page showing.
     */
    @Override
