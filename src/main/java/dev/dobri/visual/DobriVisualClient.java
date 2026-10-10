package dev.dobri.visual;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class DobriVisualClient implements ClientModInitializer {
    public static final String MOD_ID = "dobri_visual";

    private static KeyBinding menuKey;

    @Override
    public void onInitializeClient() {
        Modules.load();

        KeyBinding.Category category = KeyBinding.Category.create(Identifier.of(MOD_ID, "main"));
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.dobri_visual.menu",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new MenuScreen());
                }
            }
        });

        HudElementRegistry.attachElementAfter(
                VanillaHudElements.MISC_OVERLAYS,
                Identifier.of(MOD_ID, "hud"),
                Hud::render
        );
    }
}
