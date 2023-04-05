package de.linusdev.nat;

import de.linusdev.lutils.bitfield.IntBitFieldValue;

public enum Modifiers implements IntBitFieldValue {

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_SHIFT
     */
    SHIFT(GLFWValues.Modifiers.GLFW_MOD_SHIFT),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_CONTROL
     */
    CONTROL(GLFWValues.Modifiers.GLFW_MOD_CONTROL),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_ALT
     */
    ALT(GLFWValues.Modifiers.GLFW_MOD_ALT),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_SUPER
     */
    SUPER(GLFWValues.Modifiers.GLFW_MOD_SUPER),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_CAPS_LOCK
     */
    CAPS_LOCK(GLFWValues.Modifiers.GLFW_MOD_CAPS_LOCK),

    /**
     * @see GLFWValues.Modifiers#GLFW_MOD_NUM_LOCK
     */
    NUM_LOCK(GLFWValues.Modifiers.GLFW_MOD_NUM_LOCK),
    ;

    private final int value;

    Modifiers(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }
}
