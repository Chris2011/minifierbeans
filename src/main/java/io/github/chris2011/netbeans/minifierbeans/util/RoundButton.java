package io.github.chris2011.netbeans.minifierbeans.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Chris
 * Taken from https://www.java-forum.org/thema/runde-buttons-erstellen.182494/
 */
public class RoundButton extends JButton {
    private Shape shape;
    private int arc;
    private boolean isQuadratic;

    public RoundButton(int arc) {
        this(null, arc, false);
    }

    public RoundButton(String text, int arc) {
        this(text, arc, true);
    }

    public RoundButton(String text, int arc, boolean isQuadratic) {
        super(text);
        this.arc = arc;
        this.isQuadratic = isQuadratic;

        initComponent();
    }

    private void initComponent() {
        if (isQuadratic) {
            final Dimension size = new Dimension(18, 18);

            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);
        }

        setContentAreaFilled(false);
        setFocusable(false);
        setIcon(new ImageIcon(RoundButton.class.getResource("help.png")));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d.setColor(getModel().isArmed() ? Color.orange : getBackground());
        g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arc, arc));
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
//        Graphics2D g2d = (Graphics2D) g;
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//
//        g2d.setColor(getBackground().darker());
//        g2d.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, arc, arc));
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            this.shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arc, arc);
        }
        return shape.contains(x, y);
    }
}
