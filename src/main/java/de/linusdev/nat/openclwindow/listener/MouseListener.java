package de.linusdev.nat.openclwindow.listener;

import de.linusdev.lutils.bitfield.IntVolatileBitfield;
import de.linusdev.nat.Modifiers;
import org.jetbrains.annotations.NotNull;

public interface MouseListener {

    void onMouseCursor(double xpos, double ypos);

    void onMouseButton(int button, int action, @NotNull IntVolatileBitfield<Modifiers> mods);
}
