}

@Override
public void paintControl(PaintEvent e) {
    Point size = getSize();

    if (handleImage != null) {
        Rectangle ibounds = handleImage.getBounds();


        int x = ((size.x - 2 * margin) % ibounds.width) / 2 + margin;
        int y = ((size.y - 2 * margin) % ibounds.height) / 2 + margin;

        for (;;) {
            e.gc.drawImage(handleImage, x, y);
            if (isHorizontal) {
                x += ibounds.width;
                if (x + ibounds.width > size.x - margin) {
					break;
				}
            } else {
                y += ibounds.height;
                if (y + ibounds.height > size.y - margin) {
					break;
