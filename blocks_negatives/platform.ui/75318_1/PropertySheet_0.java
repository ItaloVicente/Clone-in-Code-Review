    /**
	 * The <code>PropertySheet</code> implementation of this
	 * <code>PageBookView</code> method handles the <code>ISaveablePart</code>
	 * adapter case by calling <code>getSaveablePart()</code>.
	 * <p>
	 * This is required to make sure that if the current part tracked by this
	 * <code>PropertySheet</code> instance is <b>not</b> contributing pages to
	 * this <code>PropertySheet</code>, we do <b>not</b> expose an
	 * <code>ISaveablePart</code> adapter to it via <code>getAdapter()</code>
	 * call to the default page. If we would do this, we would illegally add
	 * <code>ISaveablePart</code> functionality to this
	 * <code>PropertySheet</code> instance even if the target part has not
	 * contributed anything.
