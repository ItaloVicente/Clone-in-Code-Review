
package org.eclipse.jface.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;

public class FormDataFactory {

	static final int DENOMINATOR = 100;

	private final FormData formData;

	public static FormDataFactory attach(Control control) {
		return new FormDataFactory(control);
	}

	public FormDataFactory toLeft() {
		return toLeft(0);
	}

	public FormDataFactory toLeft(int margin) {
		formData.left = new FormAttachment(0, margin);
		return this;
	}

	public FormDataFactory atLeftTo(Control control) {
		return atLeftTo(control, 0);
	}

	public FormDataFactory atLeftTo(Control control, int margin) {
		return atLeftTo(control, margin, SWT.DEFAULT);
	}

	public FormDataFactory atLeftTo(Control control, int margin, int alignment) {
		formData.left = new FormAttachment(control, margin, alignment);
		return this;
	}

	public FormDataFactory fromLeft(int numerator) {
		return fromLeft(numerator, 0);
	}

	public FormDataFactory fromLeft(int numerator, int margin) {
		formData.left = new FormAttachment(numerator, margin);
		return this;
	}

	public FormDataFactory toRight() {
		return toRight(0);
	}

	public FormDataFactory toRight(int margin) {
		formData.right = new FormAttachment(DENOMINATOR, -margin);
		return this;
	}

	public FormDataFactory atRightTo(Control control) {
		atRightTo(control, 0);
		return this;
	}

	public FormDataFactory atRightTo(Control control, int margin) {
		return atRightTo(control, margin, SWT.DEFAULT);
	}

	public FormDataFactory atRightTo(Control control, int margin, int alignment) {
		formData.right = new FormAttachment(control, -margin, alignment);
		return this;
	}

	public FormDataFactory fromRight(int numerator) {
		return fromRight(numerator, 0);
	}

	public FormDataFactory fromRight(int numerator, int margin) {
		formData.right = new FormAttachment(DENOMINATOR - numerator, -margin);
		return this;
	}

	public FormDataFactory toTop() {
		return toTop(0);
	}

	public FormDataFactory toTop(int margin) {
		formData.top = new FormAttachment(0, margin);
		return this;
	}

	public FormDataFactory atTopTo(Control control) {
		return atTopTo(control, 0);
	}

	public FormDataFactory atTopTo(Control control, int margin) {
		return atTopTo(control, margin, SWT.DEFAULT);
	}

	public FormDataFactory atTopTo(Control control, int margin, int alignment) {
		formData.top = new FormAttachment(control, margin, alignment);
		return this;
	}

	public FormDataFactory fromTop(int numerator) {
		return fromTop(numerator, 0);
	}

	public FormDataFactory fromTop(int numerator, int margin) {
		formData.top = new FormAttachment(numerator, margin);
		return this;
	}

	public FormDataFactory toBottom() {
		return toBottom(0);
	}

	public FormDataFactory toBottom(int margin) {
		formData.bottom = new FormAttachment(DENOMINATOR, -margin);
		return this;
	}

	public FormDataFactory atBottomTo(Control control) {
		return atBottomTo(control, 0);
	}

	public FormDataFactory atBottomTo(Control control, int margin) {
		return atBottomTo(control, margin, SWT.DEFAULT);
	}

	public FormDataFactory atBottomTo(Control control, int margin, int alignment) {
		formData.bottom = new FormAttachment(control, -margin, alignment);
		return this;
	}

	public FormDataFactory fromBottom(int numerator) {
		return fromBottom(numerator, 0);
	}

	public FormDataFactory fromBottom(int numerator, int margin) {
		formData.bottom = new FormAttachment(DENOMINATOR - numerator, -margin);
		return this;
	}

	public FormDataFactory withWidth(int width) {
		formData.width = width;
		return this;
	}

	public FormDataFactory withHeight(int height) {
		formData.height = height;
		return this;
	}

	public FormData getFormData() {
		return formData;
	}

	private FormDataFactory(Control control) {
		formData = new FormData();
		control.setLayoutData(formData);
	}
}
