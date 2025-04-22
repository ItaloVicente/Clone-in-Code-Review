            }
        }
    }
}
@Override
public Point computeSize(int wHint, int hHint, boolean changed) {
    Point result = new Point(wHint, hHint);

    Rectangle ibounds = handleImage.getBounds();

    if (wHint == SWT.DEFAULT) {
        result.x = ibounds.width + 2 * margin;
    }

    if (hHint == SWT.DEFAULT) {
        result.y = ibounds.height + 2 * margin;
    }

    return result;
}

public void setHorizontal(boolean isHorizontal) {
    this.isHorizontal = isHorizontal;
}

@Override
public void dispose() {
    if (isDisposed()) {
        return;
    }
    super.dispose();
    handleImage.dispose();
    JFaceResources.getResources().destroyImage(descriptor);
}
