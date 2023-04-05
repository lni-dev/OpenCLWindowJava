package de.linusdev.nat.openclwindow.listener;

import de.linusdev.lutils.bitfield.IntVolatileBitfield;
import de.linusdev.nat.Modifiers;
import org.jetbrains.annotations.NotNull;

public interface KeyListener {

    void onKey(int key, int scancode, int action, @NotNull IntVolatileBitfield<Modifiers> mods);

}
