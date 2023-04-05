package de.linusdev.nat.structs;

import de.linusdev.nat.types.Float1;
import de.linusdev.nat.types.Float3;
import org.jetbrains.annotations.NotNull;

public class CameraStruct extends ComplexStructure {

    public static final StructureInfo INFO = new StructureInfo(
            Float3.INFO,
            Float3.INFO,
            Float1.INFO);

    public final @NotNull Float3 position = new Float3(false);
    public final @NotNull Float3 lookAtVector = new Float3(false);
    public final @NotNull Float1 distanceToScreen = new Float1(false);

    public CameraStruct(boolean allocateBuffer) {
        super();
        init(allocateBuffer, position, lookAtVector, distanceToScreen);
    }

    @Override
    protected @NotNull StructureInfo getInfo() {
        return INFO;
    }

    @Override
    public String toString() {
        return toString(position, lookAtVector, distanceToScreen);
    }
}
