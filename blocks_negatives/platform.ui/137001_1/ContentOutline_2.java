 * If the editor supports a content outline page, the editor instantiates
 * and configures the page, and returns it. This page is then added to the
 * content outline view (a pagebook which presents one page at a time) and
 * immediately made the current page (the content outline view need not be
 * visible). If the editor does not support a content outline page, the content
 * outline view shows a special default page which makes it clear to the user
 * that the content outline view is disengaged. A content outline page is free
 * to report selection events; the content outline view forwards these events
 * along to interested parties. When the content outline view notices a
 * different editor being activated, it flips to the editor's corresponding
 * content outline page. When the content outline view notices an editor being
 * closed, it destroys the editor's corresponding content outline page.
